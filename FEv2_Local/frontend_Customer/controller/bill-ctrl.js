app.controller('bill-ctrl', function ($scope, $http, $routeParams, $rootScope) {

    //getToken
    $scope.authToken = localStorage.getItem("authToken");
    if ($scope.authToken == null || $scope.authToken == undefined) {
        setTimeout(() => {
            document.location = '/admin#!/login';
        }, 2000);
        sweetError('Please login !');
        return;
    }
    var url_string = window.location.href;
    var url = new URL(url_string);
    console.log(url);
    $scope.user = JSON.parse(localStorage.getItem("user"));
    // $rootScope.bill = $scope.bill;
    $scope.bill = JSON.parse(localStorage.getItem("bill"));
    var statusCheck = JSON.parse(localStorage.getItem("statusCheck"));
    console.log(JSON.parse(localStorage.getItem("bill")));
    $scope.division = {};
    $scope.district = {};
    $scope.ward = {};
    $scope.products = [];
    $scope.billDetails = {};
    $scope.quantity = 1;
    var shopDistrictId = 0; 

    const API_VARIANT_VALUE = 'http://localhost:8080/user/rest/variant-values';
    const API_BILL = 'http://localhost:8080/user/rest/bills/update'
    const API_PRODUCT = "http://localhost:8080/user/rest/products";
    const API_IMAGE = 'http://localhost:8080/admin/rest/image';
    const API_BILL_DETAIL = "http://localhost:8080/user/rest/billdetails";
    const API_SETTING = 'http://localhost:8080/admin/rest/settings/get-district-id-store';
    const API_OPTION = 'http://localhost:8080/user/rest/options';
    const API_OPTION_VALUE = "http://localhost:8080/user/rest/option-values";
    const API_PRODUCT_VARIANT = 'http://localhost:8080/user/rest/product-variants';
    const apiCartDetail = `http://localhost:8080/user/rest/cartdetail`;
    const apiOptionValues = `http://localhost:8080/user/rest/option-optionvalue`;
    const apiProductVariant = `http://localhost:8080/user/rest/product-variants`;
    const apiProducts = `http://localhost:8080/user/rest/products`;

    //get shop's districtId
    function getShopDistrictId() {
        $http.get(`${API_SETTING}`)
            .then(resp => {
                shopDistrictId = resp.data.value;
                $scope.isLoading = false;
            })
            .catch(error => {
                if (error.status == 401) {
                    $scope.isLoading = false;
                    setTimeout(() => {
                        document.location = '/admin#!/login';
                    }, 2000);
                    sweetError('Mời bạn đăng nhập !');
                    return;
                }
                console.log(error);
                $scope.isLoading = false;
            })
    }
    getShopDistrictId();

    //tính giá phí ship
    $scope.shippingFee = async function () {
        $scope.isLoading = true;
        $scope.totalMoney = 0;
        console.log($scope.bill);
        $scope.bill.billDetails.forEach(de => {
            $scope.totalMoney += de.price * de.quantity * (de.tax + 100) / 100;
        });
        $scope.totalWeight = 0;
        await $scope.bill.billDetails.forEach(de => {
            $http.get(`${API_VARIANT_VALUE}/find-by-product-variant-origin/${de.productVariants.variantId}`, {
                headers: { 'Authorization': 'Bearer ' + $scope.authToken }
            })
                .then(resp => {
                    console.log(resp.data);
                    var lstVV = resp.data;
                    var index = lstVV.findIndex(v => v.optionValues.options.optionName == "CÂN NẶNG");
                    $scope.totalWeight += parseInt(lstVV[index].optionValues.valueName) * de.quantity;
                    console.log($scope.totalWeight);
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        });

        setTimeout(() => {

            if ($scope.totalWeight == 0) {
                $scope.bill.billDetails.forEach(de => {
                    $scope.totalWeight += de.quantity * 2000;
                });
            }

            //api lấy thông tin dịch vụ
            var service = {
                shop_id: 3257646,
                from_district: shopDistrictId,
                to_district: $scope.bill.districtId
            }
            $scope.isLoading = true;
            $http.post(`https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services`, service, {
                headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
            }).then(resp => {
                $scope.service_id = resp.data.data[0].service_id;
                $scope.isLoading = false;
            }).catch(error => {
                $scope.isLoading = false;
                console.log(error);
            })

            // api lấy phí ship
            var fee = {
                service_type_id: 2,
                insurance_value: $scope.totalMoney,
                coupon: null,
                from_district_id: shopDistrictId,
                to_district_id: $scope.bill.districtId,
                to_ward_code: $scope.bill.wardCode,
                height: 15,
                length: 15,
                weight: $scope.totalWeight,
                width: 15
            }
            $scope.isLoading = true;
            $http.post(`https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee`, fee, {
                headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7', 'shop_id': 3257646 }
            }).then(resp => {
                $scope.bill.shippingFee = resp.data.data.total;
                console.log(resp.data.data.total);
                $scope.isLoading = false;
            }).catch(error => {
                $scope.isLoading = false;
                console.log(error);
            })

        }, 1500);

    }

    function getProducts() {
        try {
            $http.get(`${API_PRODUCT}/find-by-status-true`)
                .then(function (response) {
                    $scope.products = response.data;
                }).catch(function (error) {
                    console.log(error.data);
                });
        } catch (error) {

        }
    }
    getProducts();

    if (JSON.parse(localStorage.getItem("newBillDetails")) != null) {
        $scope.bill.billDetails = angular.copy(JSON.parse(localStorage.getItem("newBillDetails")));
        getVariantValues($scope.bill.billDetails);
        // $scope.bill.checkReturn = false;
        $scope.bill.billDetails.forEach(de => {
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
        $scope.shippingFee();
        localStorage.removeItem("newBillDetails");
    }
    else {
        if (statusCheck) {
            $http.get(`${API_BILL_DETAIL}/${$scope.bill.billId}`, {
                headers: { 'Authorization': 'Bearer ' + $scope.authToken }
            })
                .then(resp => {
                    $scope.bill.billDetails = resp.data;
                    console.log($scope.bill.billDetails);
                    getVariantValues($scope.bill.billDetails);
                    $scope.shippingFee();
                    // $scope.bill.checkReturn = false;
                    $scope.bill.billDetails.forEach(de => {
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
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        } else {
            $http.get(`${API_BILL_DETAIL}/noReturn/${$scope.bill.billId}`, {
                headers: { 'Authorization': 'Bearer ' + $scope.authToken }
            })
                .then(resp => {
                    $scope.bill.billDetails = resp.data;
                    console.log($scope.bill.billDetails);
                    getVariantValues($scope.bill.billDetails);
                    $scope.shippingFee();
                    // $scope.bill.checkReturn = false;
                    $scope.bill.billDetails.forEach(de => {
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
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        }
    }

    // getVariantValues($scope.bill.billDetails);

    // console.log($scope.bill.billDetails);
    // if ($scope.bill.billDetails != undefined) {
    //     $scope.bill.billDetails.forEach(de => {
    //         $http.get(`${API_IMAGE}/find-by-product/${de.productVariants.variantId}`, {
    //             headers: { 'Authorization': 'Bearer ' + $scope.authToken }
    //         })
    //             .then(resp => {
    //                 de.image = resp.data[0];
    //             })
    //             .catch(error => {
    //                 console.log(error);
    //                 $scope.isLoading = false;
    //             })
    //     });
    // }

    $scope.getDivision = function () {
        $scope.lstDivision = [];
        $http.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/province', {
            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
        })
            .then(resp => {
                $scope.lstDivision = resp.data.data;
                var index = $scope.lstDivision.findIndex(c => c.ProvinceID == $scope.bill.divisionId);
                $scope.division = $scope.lstDivision[index];


                var data = {
                    province_id: parseInt($scope.division.ProvinceID)
                }
                console.log(data);

                $http.post(`https://online-gateway.ghn.vn/shiip/public-api/master-data/district`, data, {
                    headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
                })
                    .then(resp => {
                        $scope.lstDistrict = resp.data.data;
                        var index = $scope.lstDistrict.findIndex(c => c.DistrictID == $scope.bill.districtId);
                        $scope.district = $scope.lstDistrict[index];

                        var data = {
                            district_id: parseInt($scope.district.DistrictID)
                        }

                        $http.post(`https://online-gateway.ghn.vn/shiip/public-api/master-data/ward`, data, {
                            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
                        })
                            .then(resp => {
                                $scope.lstWard = resp.data.data;
                                var index = $scope.lstWard.findIndex(c => c.WardCode == $scope.bill.wardCode);
                                $scope.ward = $scope.lstWard[index];
                            })
                            .catch(error => {
                                console.log(error);
                                $scope.isLoading = false;
                            })
                    })
                    .catch(error => {
                        console.log(error);
                        $scope.isLoading = false;
                    })
            })
            .catch(error => {
                console.log(error);
            })

    }

    $scope.getDivision();

    $scope.changeDivision = function (division) {
        console.log(division);
        $scope.messageDivision = '';
        $scope.lstDistrict = [];
        $scope.lstWard = [];
        $scope.bill.divisionId = division.ProvinceID;
        $scope.bill.divisionName = division.ProvinceName;
        var data = {
            province_id: parseInt(division.ProvinceID)
        }

        $http.post(`https://online-gateway.ghn.vn/shiip/public-api/master-data/district`, data, {
            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
        })
            .then(resp => {
                $scope.lstDistrict = resp.data.data;
            })
            .catch(error => {
                console.log(error);
                $scope.isLoading = false;
            })
    }

    $scope.changeDistrict = function (district) {
        if (district != null) {
            $scope.messageDistrict = '';
            $scope.lstWard = [];
            $scope.bill.districtId = district.DistrictID;
            $scope.bill.districtName = district.DistrictName;
            var data = {
                district_id: parseInt(district.DistrictID)
            }

            $http.post(`https://online-gateway.ghn.vn/shiip/public-api/master-data/ward`, data, {
                headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
            })
                .then(resp => {
                    $scope.lstWard = resp.data.data;
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        }
    }

    $scope.changeWard = function (ward) {
        console.log(ward);
        $scope.messageWard = '';
        if (ward != null) {
            $scope.bill.wardName = ward.WardName;
            $scope.bill.wardCode = ward.WardCode;
        }
        $scope.shippingFee();
        console.log($scope.bill);
    }

    //changeQuantity
    $scope.changeQuantity = function (index) {
        console.log($scope.bill);
        if ($scope.bill.billDetails[index].quantity == undefined || !Number.isInteger($scope.bill.billDetails[index].quantity)) {
            toastMessage("error", "Số lượng không phù hợp !");
            $scope.bill.billDetails[index].quantity = 1;
            $scope.shippingFee();
            return;
        }
        $scope.shippingFee();
    }

    //ordersTab
    $scope.ordersTab = function () {
        $rootScope.showTab = 2;
        window.location.href = '/frontend_Customer/index.html#!/dashboard';
    }

    //ordersTab
    $scope.accountDetailTab = function () {
        $rootScope.showTab = 3;
        window.location.href = '/frontend_Customer/index.html#!/dashboard';
    }

    $scope.updateBill = function () {
        console.log($scope.bill);
        if ($scope.bill.wardName == null || $scope.bill.wardName == "" || $scope.bill.wardName == undefined) {
            toastMessage("error", "Địa chỉ chưa đầy đủ !");
            return;
        }
        Swal.fire({
            title: 'Bạn có muốn ? ',
            text: "Cập nhật hóa đơn !",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes !'
        }).then((result) => {
            if (result.isConfirmed) {
                $scope.isLoading = true;
                var data = {
                    bill: $scope.bill,
                    billDetails: $scope.bill.billDetails
                }
                console.log(data);
                $http.put(`${API_BILL}`, data, {
                    headers: { 'Authorization': 'Bearer ' + $scope.authToken }
                })
                    .then(resp => {
                        console.log(resp.data);
                        toastMessage("success", "Cập nhật thành công !");
                        $scope.btnReturn();
                        $scope.isLoading = false;
                    })
                    .catch(error => {
                        console.log(error);
                        $scope.isLoading = false;
                    })
            }
        })
    }

    //deleteDetailBill
    $scope.deleteDetailBill = function (index) {
        if ($scope.bill.billDetails.length <= 1) {
            toastMessage('warning', 'Hóa đơn phải có ít nhất 1 sản phẩm');
            return
        }
        Swal.fire({
            title: 'Bạn có chắc chắn?',
            text: "Sản phẩm này sẽ bị xóa!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                $scope.bill.billDetails.splice(index, 1);
                $scope.shippingFee();
                toastMessage("success", "Xóa thành công !");
            }
        })
    }

    //btnReturn
    $scope.btnReturn = function () {
        $rootScope.showTab = 2;
        window.location.href = '/frontend_Customer/index.html#!/dashboard'
    }

    //buyMore
    $scope.buyMore = function () {
        console.log($scope.bill.billDetails);
        localStorage.setItem("billDetails", angular.copy(JSON.stringify($scope.bill.billDetails)));
        window.location.href = '/frontend_Customer/index.html#!/buymore'
    }

    async function getVariantValues(arr) {
        if (arr != undefined) {
            await arr.forEach(element => {
                console.log(element.productVariants);
                $http.get(`${API_VARIANT_VALUE}/find-by-product-variant/${element.productVariants.variantId}`, {
                    headers: { 'Authorization': 'Bearer ' + $scope.authToken }
                })
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
    }







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


    function getImages() {
        if ($routeParams.id != undefined) {
            $http.get(`${API_IMAGE}/find-by-product-origin/${$routeParams.id}`)
                .then(resp => {
                    console.log(resp.data);
                    $scope.imageOrigins = resp.data;
                    $scope.images = $scope.imageOrigins;
                    $scope.imageSelected = $scope.imageOrigins[0];
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        }
    }
    getImages();

    //get option 
    if ($routeParams.id != undefined) {
        $http.get(`${apiOptionValues}/find-by-product/${$routeParams.id}`)
            .then(function (response) {
                $scope.options_main = response.data;
            })
            .catch(function (error) {
                console.log(error);
            }
            )
    }
    // //get price
    if ($routeParams.id != undefined) {
        $http.get(`${apiProductVariant}/get-price-range/${$routeParams.id}`)
            .then(function (response) {
                $scope.price = response.data.price;
            })
            .catch(function (error) {
                console.log(error);
            }
            )
    }
    //get product
    if ($routeParams.id != undefined) {
        $http.get(`${apiProducts}/find-by-id/${$routeParams.id}`)
            .then(function (response) {
                $scope.product = response.data;
            })
            .catch(function (error) {
                console.log(error);
            }
            )
    }
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
                            console.log(resp.data);
                            $scope.images = resp.data;
                            $scope.imageSelected = resp.data[0];
                        })
                        .catch(error => {
                            console.log(error);
                            $scope.isLoading = false;
                        })

                    $scope.productVariants = response.data;
                    $scope.cartDetail.price = response.data.price;
                    document.getElementById('addCart').classList.remove('isProductVariant')
                    console.log(response.data);
                    checkSale($scope.productVariants)
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

    $scope.addToCart = async function () {
        if ($scope.productVariants == undefined) {
            toastMessage("error", "Chưa chọn thuộc tính sản phẩm !");
            return;
        }
        Swal.fire({
            title: 'Bạn có muốn ? ',
            text: "Thêm sản phẩm vào hóa đơn hiện tại !",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes !'
        }).then((result) => {
            if (result.isConfirmed) {
                $scope.check = 0;
                $scope.billDetails = angular.copy(JSON.parse(localStorage.getItem("billDetails")));
                console.log($scope.billDetails);
                $scope.billDetails.forEach(de => {
                    if (de.productVariants.variantId == $scope.productVariants.variantId) {
                        if (de.quantity + $scope.quantity > $scope.productVariants.quantity || $scope.quantity == undefined || !Number.isInteger($scope.quantity)) {
                            $scope.check = 2;
                            return;
                        }
                        de.quantity += $scope.quantity;
                        $scope.check = 1;
                    }
                });
                console.log($scope.check);
                if ($scope.check == 1) {
                    localStorage.setItem("newBillDetails", angular.copy(JSON.stringify($scope.billDetails)));
                    console.log($scope.billDetails);
                    // return;
                } else if ($scope.check == 0) {
                    if ($scope.quantity > $scope.productVariants.quantity || $scope.quantity == undefined || !Number.isInteger($scope.quantity)){
                        toastMessage("error", "Số lượng không phù hợp !");
                        return;
                    }
                    if ($scope.priceSale != 0) {
                        $scope.productVariants.price = $scope.priceSale;
                    }
                    var billDetail = {
                        price: $scope.productVariants.price,
                        quantity: $scope.quantity,
                        status: 0,
                        tax: $scope.productVariants.tax,
                        productVariants: $scope.productVariants,
                        bills: $scope.bill
                    }
                    $scope.billDetails.push(billDetail);
                    localStorage.setItem("newBillDetails", angular.copy(JSON.stringify($scope.billDetails)));
                    console.log($scope.billDetails);
                } else {
                    toastMessage("error", "Số lượng không phù hợp !");
                    return;
                }
                toastMessage('success', "Thêm sản phẩm thành công");
                window.location.href = '/frontend_Customer/index.html#!/bill';
            }
        })
    }

    //////////// v2 //////////////////////////////////
    async function loadAllProducts() {
        $scope.isLoading = true;
        await $http.get(`${API_PRODUCT}/find-by-status-true`)
            .then(function (response) {
                $scope.isLoading = false;
                $scope.products = response.data;
                $scope.listPage = getListPaging(response.data.length, $scope.pageSize, $scope.currentPage, $scope.pageInList);
            }).catch(function (error) {
                $scope.isLoading = false;
            });

        await $scope.products.forEach(p => {
            $scope.isLoading = true;
            $http.get(`${API_IMAGE}/find-by-product-origin/${p.productId}`)
                .then(resp => {
                    console.log(resp.data);
                    p.images = resp.data;
                    $scope.isLoading = false;
                    // $scope.images = $scope.imageOrigins;
                    // $scope.imageSelected = $scope.imageOrigins[0];
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        });
    }
    loadAllProducts();


    $(async function () {
        $scope.isLoading = true;
        await $http.get(`${API_PRODUCT_VARIANT}/price-ranges`)
            .then(resp => {
                priceMin = resp.data.body[0];
                priceMax = resp.data.body[1];
                $scope.isLoading = false;
            })
            .catch(error => {
                console.log(error);
                $scope.isLoading = false;
            })
        $("#slider-range").slider({
            range: true,
            min: priceMin,
            max: priceMax,
            values: [priceMin, priceMax],
            step: 10000,
            slide: function (event, ui) {
                $("#amount").val(ui.values[0].toLocaleString('vi-VN', {
                    style: 'currency',
                    currency: 'VND'
                }) + " - " + ui.values[1].toLocaleString('vi-VN', {
                    style: 'currency',
                    currency: 'VND'
                }));
            }
        });
        $("#amount").val($("#slider-range").slider("values", 0).toLocaleString('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }) +
            " - " + $("#slider-range").slider("values", 1).toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }));
    });

    //Get All Option and Option Value of Filter
    async function getOptionOfFilter() {
        $scope.isLoading = true;
        await $http.get(`${API_OPTION}`)
            .then(resp => {
                $scope.listOptionOfFilters = resp.data;
                $scope.isLoading = false;
            })
            .catch(error => {
                console.log(error);
                $scope.isLoading = false;
            })
        $scope.isLoading = true;
        await $scope.listOptionOfFilters.forEach(op => {
            $http.get(`${API_OPTION_VALUE}/find-by-option/${op.optionId}`)
                .then(resp => {
                    $scope.isLoading = false;
                    op.optionValues = resp.data;
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        });

    }
    getOptionOfFilter();

    $scope.filterProduct = async function () {
        var min = $("#slider-range").slider("values", 0);
        var max = $("#slider-range").slider("values", 1);
        var data = {
            optionValues: [],
            min: min,
            max: max
        }
        var checked = document.getElementsByName('option-value-selected');
        for (let i = 0; i < checked.length; i++) {
            if (checked[i].checked) {
                data.optionValues.push(JSON.parse(checked[i].value))
            }
        }

        $scope.isLoading = true;
        await $http.post(`${API_PRODUCT}`, data)
            .then(resp => {
                $scope.products = resp.data;
                $scope.isLoading = false;
            })
            .catch(error => {
                console.log(error);
                $scope.isLoading = false;
            })
        await $scope.products.forEach(p => {
            $scope.isLoading = true;
            $http.get(`${API_IMAGE}/find-by-product-origin/${p.productId}`)
                .then(resp => {
                    console.log(resp.data);
                    p.images = resp.data;
                    $scope.isLoading = false;
                    // $scope.images = $scope.imageOrigins;
                    // $scope.imageSelected = $scope.imageOrigins[0];
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        });
    }

    $scope.removeFilterProduct = function () {
        loadAllProducts();
    }

    $scope.showFilterOption = function (value) {
        var style = document.createElement('style');
        if (!$("#optionFilter" + value).hasClass('show')) {
            $("#optionFilter" + value).collapse('show');
            style.innerHTML = `
            .widget-collapsible #widget-title${value} a:after {
                content: '\\f110';
            }
            `;
        } else {
            $("#optionFilter" + value).collapse('hide');
            style.innerHTML = `
            .widget-collapsible #widget-title${value} a:after {
                content: '\\e802';
            }
            `;
        }
        document.head.appendChild(style);
    }

    $scope.showFilterPrice = function () {
        var style = document.createElement('style');
        if (!$("#collapsePrice").hasClass('show')) {
            $("#collapsePrice").collapse('show');
            style.innerHTML = `
            .widget-collapsible .widget-title-price a:after {
                content: '\\f110';
            }
            `;
        } else {
            $("#collapsePrice").collapse('hide');
            style.innerHTML = `
            .widget-collapsible .widget-title-price a:after {
                content: '\\e802';
            }
            `;
        }
        document.head.appendChild(style);
    }

    //Page
    $scope.begin = 0; // hiển thị thuộc tính bắt đầu từ 0
    $scope.pageSize = 12; // Hiển thị 10 thuộc tính
    $scope.currentPage = 1;
    $scope.pageInList = 5;

    //selectPage
    $scope.selectPage = function (page) {
        $scope.begin = (page - 1) * $scope.pageSize;
        $scope.currentPage = page;
    }

    //nextPage
    $scope.nextPage = function () {
        $scope.begin = ($scope.currentPage + 1 - 1) * $scope.pageSize;
        $scope.currentPage++;
    }

    $scope.prevPage = function () {
        $scope.begin = ($scope.currentPage - 1 - 1) * $scope.pageSize;
        $scope.currentPage--;
    }

    $scope.nextListPage = function () {
        $scope.currentPage = $scope.endListPage + 1;
        $scope.begin = ($scope.currentPage - 1) * $scope.pageSize;
        $scope.listPage = getListPaging($scope.products.length, $scope.pageSize, $scope.currentPage, $scope.pageInList)
    }

    $scope.prevListPage = function () {
        $scope.currentPage = $scope.startListPage - $scope.pageInList;
        $scope.begin = ($scope.currentPage - 1) * $scope.pageSize;
        $scope.listPage = getListPaging($scope.products.length, $scope.pageSize, $scope.currentPage, $scope.pageInList)
    }

    function getListPaging(totalRecord, limit, currentPage, pageInList) {
        $scope.isLoading = true;
        var listPage = [];
        var totalPage = parseInt(totalRecord / limit);
        if (totalRecord % limit != 0) {
            totalPage++;
        }
        var startPage = getStartPage(currentPage, pageInList);
        var endPage = getEndPage(startPage, pageInList, totalPage);
        for (let i = startPage; i <= endPage; i++) {
            listPage.push(i);
        }
        $scope.totalPage = totalPage;
        $scope.isLoading = false;
        return listPage;
    }

    function getStartPage(currentPage, limit) {
        var startPage = (parseInt((currentPage - 1) / limit) * limit + 1);
        $scope.startListPage = startPage;
        return startPage;
    }

    function getEndPage(startPage, limit, totalPage) {
        var endPage = startPage + limit - 1;
        if (endPage > totalPage) {
            endPage = totalPage;
        }
        $scope.endListPage = parseInt(endPage);
        return parseInt(endPage);
    }
    //////////////////////////////////////////////////

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