app.controller('detail_controller', function ($scope, $http, $routeParams, $rootScope) {
    $scope.options_main = [];
    $scope.options_get = {
        product: {},
        options: [],
        optionValues: [],
    };
    $scope.product = [];
    $scope.price = {};
    $scope.cartDetail = {}
    $scope.images = [];
    $scope.imageSelected = {};
    $rootScope.isReload = false;

    $scope.quantity = 1;

    $scope.productNameView = $routeParams.productName

    const apiCartDetail = `http://localhost:8080/user/rest/cartdetail`;
    const apiOptionValues = `http://localhost:8080/user/rest/option-optionvalue`;
    const apiProductVariant = `http://localhost:8080/user/rest/product-variants`;
    const apiProducts = `http://localhost:8080/user/rest/products`;
    const API_IMAGE = 'http://localhost:8080/admin/rest/image';

    function getCart() {
        if (sessionStorage.cart != null) {
            $scope.cartDetail.carts = JSON.parse(sessionStorage.cart)[0]
        }
    }
    $scope.productVariants
    getCart()

    $http.get(`${API_IMAGE}/find-by-product-origin/${$routeParams.id}`)
        .then(resp => {
            console.log(resp.data);
            $scope.imageOrigins = resp.data;
            $scope.images = $scope.imageOrigins;
            $scope.imageSelected = $scope.imageOrigins[0];
            $scope.isLoading = false;
        })
        .catch(error => {
            console.log(error);
            $scope.isLoading = false;
        })

    //get option 
    $http.get(`${apiOptionValues}/find-by-product/${$routeParams.id}`)
        .then(function (response) {
            $scope.options_main = response.data;
        })
        .catch(function (error) {
            console.log(error);
        }
        )
    //get price
    $http.get(`${apiProductVariant}/get-price-range/${$routeParams.id}`)
        .then(function (response) {
            $scope.price = response.data.price;
        })
        .catch(function (error) {
            console.log(error);
        }
        )
    //get product
    $http.get(`${apiProducts}/find-by-id/${$routeParams.id}`)
        .then(function (response) {
            $scope.product = response.data;
        })
        .catch(function (error) {
            console.log(error);
        }
        )
    // Tìm kiếm option theo optionId
    function search(id) {
        for (var i = 0; i < $scope.options_main.length; i++) {
            if ($scope.options_main[i].option.optionId === id) {
                return $scope.options_main[i];
            }
        }
    }
    function searchVa(id, myArr) {
        for (var i = 0; i < myArr.length; i++) {
            if (myArr[i].valueId === id) {
                return myArr[i];
            }
        }
    }
    //Select option
    $scope.onSelect = async function (valueId, optionId) {
        // reset active
        var option = document.getElementsByClassName('option' + optionId)
        for (let i = 0; i < option.length; i++) {
            if (option[i].id != "valueId" + valueId) {
                if (option[i].classList.contains("active")) {
                    option[i].classList.toggle("active")
                }
            }
        }
        // add active 
        document.getElementById('valueId' + valueId).classList.toggle("active")

        //lấy dữ liệu đã select vào object Test
        if (document.getElementById('valueId' + valueId).classList.contains("active")) {
            $scope.objectTest = {
                option: search(optionId).option,
                optionValues: searchVa(valueId, search(optionId).optionValues),
            };
        } else {
            $scope.objectTest = valueId
        }
        //check và thêm objectTest vào obtion get để lấy ra option
        pushObject($scope.objectTest);
    }
    //make select option
    function pushObject(myArr) {
        if ($scope.options_get.options.length == 0 && $scope.options_get.optionValues.length == 0) {
            $scope.options_get.options.push(myArr.option);
            $scope.options_get.optionValues.push(myArr.optionValues);
        } else if (!isNaN(myArr)) {
            $scope.options_get.optionValues.forEach(element => {
                if (element.valueId == myArr) {
                    var index = $scope.options_get.optionValues.indexOf(element)
                    $scope.options_get.optionValues.splice(index, 1)
                    $scope.options_get.options.splice(index, 1)
                }
            });
        } else {
            let a = 0;
            for (let i = 0; i < $scope.options_get.options.length; i++) {
                // console.log($scope.options_get[i].options.optionId +"|||"+ myArr.option.optionId);
                if ($scope.options_get.options[i].optionId == myArr.option.optionId) {
                    $scope.options_get.options.splice(i, 1, myArr.option);
                    $scope.options_get.optionValues.splice(i, 1, myArr.optionValues);
                    a++;
                }
            }
            if (a == 0) {
                $scope.options_get.options.push(myArr.option);
                $scope.options_get.optionValues.push(myArr.optionValues);
            }
        }
        $scope.options_get.product = $scope.product
        // console.log($scope.options_get);
        $http.post(`${apiProductVariant}/find-by-product-option-optionvalue`, $scope.options_get)
            .then(function (response) {
                if (response.data == "") {
                    $scope.productVariants = null;
                    $scope.images = $scope.imageOrigins;
                    $scope.imageSelected = $scope.imageOrigins[0];
                    document.getElementById('addCart').classList.add('isProductVariant')
                } else {
                    $scope.images = [];
                    $http.get(`${API_IMAGE}/find-by-product/${response.data.variantId}`)
                        .then(resp => {
                            $scope.images = resp.data;
                            $scope.imageSelected = resp.data[0];
                        })
                        .catch(error => {
                            console.log(error);
                            $scope.isLoading = false;
                        })
                    $scope.productVariants = response.data;
                    $scope.cartDetail.price = response.data.price;
                    checkSale($scope.productVariants)
                    document.getElementById('addCart').classList.remove('isProductVariant')
                }
            })
            .catch(function (error) {
                console.error(error);
            })
    }

    $scope.selectImage = function (image) {
        $scope.imageSelected = image;
    }

    function checkSale(productVariants) {
        $http.get(`${apiProductVariant}/find-discount-sale-by-product-variant/${productVariants.variantId}`)
            .then(function (response) {
                if (response.data.discount != 0) {
                    $scope.priceSale = productVariants.price - response.data.discount;
                } else {
                    $scope.priceSale = 0;
                }
            })
            .catch(function (error) {
                console.log(error);
            }
            )
    }
    //get select option
    function getCartDetails(cartId) {
        $http.get(`${apiCartDetail}/find-by-cart/${cartId}`)
            .then(function (response) {
                sessionStorage.setItem('cartDetail', JSON.stringify(response.data))
                $rootScope.cartDetail = JSON.parse(sessionStorage.cartDetail)
                $rootScope.cartDetail.forEach(element => {
                    $rootScope.subTotal += element.productVariants.price * element.quantity;
                });
            }).catch(function (error) {
                console.error(error);
            })
    }

    $scope.addToCart = async function () {
        if (sessionStorage.authToken2 != null) {
            let count = 0;
            if ($scope.options_get.options.length == 0) {
                toastMessage("warning", "Chọn thuộc tính để thêm vào giỏ hàng !")
            } else {
                $rootScope.cartDetail.forEach(element => {
                    if (element.productVariants.variantId == $scope.productVariants.variantId) {
                        count++;
                        $scope.cartDetail = element;
                    }
                });
                if ($scope.cartDetail.quantity == null || ($scope.productVariants.quantity > $scope.quantity)) {
                    if (count != 0) {
                        if (($scope.quantity + $scope.cartDetail.quantity) <= $scope.productVariants.quantity) {
                            $scope.cartDetail.quantity += $scope.quantity;
                            $http.put(`${apiCartDetail}/${$scope.cartDetail.cartDetailId}`, $scope.cartDetail)
                                .then(function (response) {
                                    toastMessage('success', 'Thêm mới thành công !')
                                })
                                .catch(function (error) {
                                    console.error(error);
                                })
                        } else {
                            toastMessage("warning", "Số lượng trong kho không đủ !")
                        }
                    } else {
                        getCart()
                        $scope.cartDetail.quantity = $scope.quantity;
                        $scope.cartDetail.createdDate = "";
                        $scope.cartDetail.productVariants = $scope.productVariants;
                        $http.post(`${apiCartDetail}/`, $scope.cartDetail)
                            .then(function (response) {
                                getCartDetails($scope.cartDetail.carts.cartId)
                                toastMessage('success', 'Thêm mới thành công !')
                            })
                            .catch(function (error) {
                                console.error(error);
                                toastMessage("warning", "Số lượng trong kho không đủ !")
                            })
                    }
                } else {
                    toastMessage("warning", "Số lượng trong kho không đủ !")
                }
            }
        } else {
            Swal.fire({
                title: 'Đăng nhập để thêm vào giỏ hàng ? ',
                text: "Đăng nhập !",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đăng nhập !'
            }).then((result) => {
                if (result.isConfirmed) {
                    $rootScope.showOtp = 0;
                    $('#signin-modal').modal('show');
                }
            })
        }
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