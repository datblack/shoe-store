app.controller('cart-controller', function ($scope, $http, $routeParams, $rootScope) {
    // $scope.cartDetail = []
    //lấy cart theo id

    $scope.total = 0;
    $scope.checkedCartDetails = [];
    const apiCartDetail = `http://localhost:8080/user/rest/cartdetail`;
    const apiVariantValues = `http://localhost:8080/user/rest/variant-values`;
    const apiProductVariant = `http://localhost:8080/user/rest/product-variants`;
    const API_IMAGE = 'http://localhost:8080/admin/rest/image';

    function getCartDetails(cartId) {
        $http.get(`${apiCartDetail}/find-by-cart/${cartId}`)
            .then(function (response) {
                $rootScope.subTotal = 0;
                sessionStorage.setItem('cartDetail', JSON.stringify(response.data))
                $rootScope.cartDetail = JSON.parse(sessionStorage.cartDetail)
                getVariantValues($rootScope.cartDetail)
                $rootScope.cartDetail.forEach(element => {
                    checkSale(element.productVariants, element.quantity)
                });
                $rootScope.cartDetail.forEach(de => {
                    $http.get(`${API_IMAGE}/find-by-product/${de.productVariants.variantId}`, {
                        headers: { 'Authorization': 'Bearer ' + $scope.authToken }
                    })
                        .then(resp => {
                            de.image = resp.data[0];
                        })
                        .catch(error => {
                            console.log(error);
                            $scope.isLoading = false;
                        })
                });
                console.log($scope.cartDetail);
            })
            .catch(function (error) {
                console.error(error);
            })
    }
    function checkSale(productVariants, quantity) {
        $http.get(`${apiProductVariant}/find-discount-sale-by-product-variant/${productVariants.variantId}`)
            .then(function (response) {
                if (response.data.discount != 0) {
                    productVariants.priceSale = angular.copy(productVariants.price - response.data.discount);
                    $rootScope.subTotal += productVariants.priceSale * quantity;
                } else {
                    productVariants.priceSale = angular.copy(0);
                    $rootScope.subTotal += productVariants.price * quantity;
                }
            })
            .catch(function (error) {
                console.log(error);
            }
            )
    }

    function getVariantValues(arr) {
        arr.forEach(element => {
            $http.get(`${apiVariantValues}/find-by-product-variant/${element.productVariants.variantId}`)
                .then(function (response) {
                    // console.log(element);
                    // element.add(JSON.stringify(response.data))
                    $scope.variantValues = {
                        variantValues: response.data
                    }
                    element = Object.assign(element, $scope.variantValues)
                })
        });
    }

    function checkCart() {
        if (sessionStorage.cart != null) {
            getCartDetails(JSON.parse(sessionStorage.cart)[0].cartId)
        }
    }
    checkCart();
    $scope.deleteCart = function (cartDetailId) {
        Swal.fire({
            title: 'Bạn có chắc chắn muốn xóa?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xóa!'
        }).then((result) => {
            if (result.isConfirmed) {
                $http.delete(`${apiCartDetail}/${cartDetailId}`)
                    .then(function (response) {
                        getCartDetails(JSON.parse(sessionStorage.cart)[0].cartId)
                        toastMessage("success", "Xóa thành công !")
                    })
                    .catch(function (error) {
                        toastMessage("danger", error)
                    })
            }
        })
    }
    function getCartChecked() {
        $scope.idCartChecked = [];
        var checkedValue = document.getElementsByName('cartdetailcheckbox');
        for (let i = 0; i < checkedValue.length; i++) {
            if (checkedValue[i].checked == true) {
                $scope.idCartChecked.push(checkedValue[i].value);

            }
        }
    }

    $scope.checkAllCart = function () {
        checkboxes = document.getElementsByName('cartdetailcheckbox');
        checkAll = document.getElementById('checkAll');
        if (checkAll.checked) {
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = false;
                $scope.checkedCartDetails = [];
            }
        } else {
            for (var i = 0; i < checkboxes.length; i++) {
                checkboxes[i].checked = true;
                $scope.checkedCartDetails = $scope.cartDetail;
            }
        }
        $scope.totalMoney();
    }

    $scope.clickCheck = function () {
        $scope.checkedCartDetails = [];
        var checkedValue = document.getElementsByName('cartdetailcheckbox');
        for (let i = 0; i < checkedValue.length; i++) {
            if (checkedValue[i].checked == true) {
                var index = $scope.cartDetail.findIndex(c => c.cartDetailId == checkedValue[i].value);
                console.log($scope.cartDetail[index]);
                $scope.checkedCartDetails.push($scope.cartDetail[index]);
            }
        }

        $scope.totalMoney();
    }

    $scope.totalMoney = function () {
        console.log($scope.cartDetailChecked);
        $scope.total = 0;
        $scope.checkedCartDetails.forEach(c => {
            $scope.total += c.productVariants.price * c.quantity * (c.productVariants.tax + 100) / 100;
        });

    }

    $scope.updateCart = function (val, id) {
        $http.get(`${apiCartDetail}/${id}`)
            .then(res => {
                res.data;
                res.data.quantity = val;
                $rootScope.isLoading = true;
                if (res.data.productVariants.quantity < res.data.quantity) {
                    toastMessage("error", "Số lượng trong kho không đủ!")
                    window.location.href="#!cart"
                    $rootScope.isLoading = false;
                } else {
                    $http.put(`${apiCartDetail}/${id}`, res.data)
                    .then(res => {
                        $rootScope.subTotal = 0
                        $rootScope.cartDetail.forEach(element => {
                            checkSale(element.productVariants, element.quantity);
                        });
                        $rootScope.isLoading = false;
                        toastMessage("success", "Cập nhật thành công !")
                    }).catch(err => {
                        $rootScope.isLoading = false;
                        window.location.href="#!cart"
                        console.log(err);
                    })
                }
            }).catch(err => {
                console.log(err);
                toastMessage("error", "error !")
            })
    }

    $scope.getCartDetailToBuy = async function () {
        $rootScope.cartDetailChecked = [];
        getCartChecked();
        if ($scope.idCartChecked == 0) {
            toastMessage('warning', 'Mời bạn chọn sản phẩm !')
        } else {
            $scope.cartDetailChecked = []

            setTimeout(() => {

                window.location.href = '/frontend_Customer/index.html#!/checkout'
            }, 1000);
            await $scope.idCartChecked.forEach(element => {
                $http.get(`${apiCartDetail}/${element}`)
                    .then(res => {
                        $scope.cartDetailChecked.push(res.data);
                        $rootScope.cartDetailChecked.push(res.data);
                    }).catch(err => {
                        console.log(err);
                    })
            });
        }
        console.log($scope.cartDetailChecked);
        // $rootScope.cartDetailChecked = angular.copy($scope.cartDetailChecked);
        console.log($rootScope.cartDetailChecked);
    }

    function toastMessage(icon, title) {
        Swal.fire({
            position: 'top-end',
            icon: icon,
            toast: true,
            animation: true,
            timerProgressBar: true,
            title: title,
            showConfirmButton: false,
            timer: 3000
        })
    }
})