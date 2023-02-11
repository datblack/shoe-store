app.controller('checkout-ctrl', function ($scope, $http, $routeParams, $rootScope) {
    //getToken
    $scope.authToken = localStorage.getItem("authToken");
    if ($scope.authToken == null || $scope.authToken == undefined) {
        setTimeout(() => {
            document.location = '/admin#!/login';
        }, 2000);
        sweetError('Please login !');
        return;
    }
    console.log($rootScope.cartDetailChecked);
    if ($rootScope.cartDetailChecked == undefined) {
        console.log('avc');
        toastMessage('warning', 'Hãy chọn sản phẩm để thanh toán !');
        window.location.href = '/frontend_Customer/index.html#!/cart';
        $scope.isLoading = false;
        return;
    }

    $scope.user = JSON.parse(localStorage.getItem("user"));
    console.log($scope.user);

    $scope.division = {};
    $scope.district = {};
    $scope.ward = {};

    if ($scope.user.divisionId != undefined)

        $scope.lstDivision = [];
    $scope.lstDistrict = [];
    $scope.lstWard = [];
    $scope.address = {};
    $scope.bill = {
        customers: $scope.user,
        phone: $scope.user.phone,
        payments: 0,
        address: $scope.user.address,
        shippingFee: 0,
        status: 1
    };
    $scope.totalMoney = 0;
    $scope.totalWeight = 0;
    $scope.messageAddress = "";
    var shopDistrictId = 0;

    const API_VARIANT_VALUE = 'http://localhost:8080/user/rest/variant-values';
    const API_BILL_USER = 'http://localhost:8080/user/rest/bills';
    const API_USER = 'http://localhost:8080/user/rest/customer';
    const API_SETTING = 'http://localhost:8080/admin/rest/settings/get-district-id-store';
    const API_VNPAY = 'http://localhost:8080/api/vnpay/user'

    //get shop's districtId
    function getShopDistrictId () {
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

    //tổng tiền sản phẩm
    $scope.sumMoney = function () {
        console.log($rootScope.cartDetailChecked.length);
        for (let i = 0; i < $rootScope.cartDetailChecked.length; i++) {
            $scope.totalMoney += $rootScope.cartDetailChecked[i].quantity * $rootScope.cartDetailChecked[i].productVariants.price * ($rootScope.cartDetailChecked[i].productVariants.tax + 100) / 100;
            console.log($rootScope.cartDetailChecked[i]);
        }
    }
    $scope.sumMoney();

    // tổng cân nặng sản phẩm
    $scope.sumWeight = async function () {
        await $rootScope.cartDetailChecked.forEach(c => {

            $http.get(`${API_VARIANT_VALUE}/find-by-product-variant-origin/${c.productVariants.variantId}`, {
                headers: { 'Authorization': 'Bearer ' + $scope.authToken }
            })
                .then(resp => {
                    console.log(resp.data);
                    var lstVV = resp.data;
                    var index = lstVV.findIndex(v => v.optionValues.options.optionName == "CÂN NẶNG");
                    if(index != -1){
                    $scope.totalWeight += parseInt(lstVV[index].optionValues.valueName) * c.quantity;
                    console.log($scope.totalWeight);
                    }
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })

        });
    }
    $scope.sumWeight();



    function getVariantValues(arr) {
        arr.forEach(element => {
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
    getVariantValues($rootScope.cartDetailChecked);

    $scope.getDivision = function () {
        $scope.lstDivision = [];
        $http.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/province', {
            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
        })
            .then(resp => {
                $scope.lstDivision = resp.data.data;
                var index = $scope.lstDivision.findIndex(c => c.ProvinceID == $scope.user.divisionId);
                $scope.address.division = $scope.lstDivision[index];
                $scope.bill.divisionId = $scope.address.division.ProvinceID;
                $scope.bill.divisionName = $scope.address.division.ProvinceName;

                var data = {
                    province_id: parseInt($scope.address.division.ProvinceID)
                }
                console.log(data);

                $http.post(`https://online-gateway.ghn.vn/shiip/public-api/master-data/district`, data, {
                    headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
                })
                    .then(resp => {
                        $scope.lstDistrict = resp.data.data;
                        var index = $scope.lstDistrict.findIndex(c => c.DistrictID == $scope.user.districtId);
                        $scope.address.district = $scope.lstDistrict[index];
                        $scope.bill.districtId = $scope.address.district.DistrictID;
                        $scope.bill.districtName = $scope.address.district.DistrictName;

                        var data = {
                            district_id: parseInt($scope.address.district.DistrictID)
                        }

                        $http.post(`https://online-gateway.ghn.vn/shiip/public-api/master-data/ward`, data, {
                            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
                        })
                            .then(resp => {
                                $scope.lstWard = resp.data.data;
                                var index = $scope.lstWard.findIndex(c => c.WardCode == $scope.user.wardCode);
                                $scope.address.ward = $scope.lstWard[index];
                                $scope.bill.wardCode = $scope.address.ward.WardCode;
                                $scope.bill.wardName = $scope.address.ward.WardName;
                                $scope.shippingFee();
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
        if (division != null) {
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
                    console.log($scope.address);
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        }
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
                    console.log($scope.address);
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        }
    }

    $scope.changeWard = function (ward) {
        console.log(ward);
        if (ward != null) {
            $scope.messageWard = '';
            $scope.bill.wardName = ward.WardName;
            $scope.bill.wardCode = ward.WardCode;
            $scope.shippingFee();
        }
    }

    //checkbox female
    $scope.clickFemale = function () {
        var female = document.getElementsByName('female');
        var male = document.getElementsByName('male');
        if ($scope.bill.payments == 0) {
            male.checked = true;
        } else {
            female.checked = true;
        }
        console.log($scope.bill);
    }

    $scope.clickMale = function () {
        var male = document.getElementsByName('male');
        var female = document.getElementsByName('female');
        if ($scope.bill.payments == 0) {
            female.checked = true;
        } else {
            male.checked = true;
        }
        console.log($scope.bill);
    }


    //tính giá phí ship
    $scope.shippingFee = async function () {
        $scope.isLoading = true;
        if($scope.totalWeight == 0){
            $scope.totalWeight = 2000;
        }
        console.log($scope.totalWeight);
        //api lấy thông tin dịch vụ
        var service = {
            shop_id: 3257646,
            from_district: shopDistrictId,
            to_district: $scope.address.district.DistrictID
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
            to_district_id: $scope.address.district.DistrictID,
            to_ward_code: $scope.address.ward.WardCode,
            height: 15,
            length: 15,
            weight: $scope.totalWeight,
            width: 15
        }
        console.log(fee);
        $scope.isLoading = true;
        $http.post(`https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee`, fee, {
            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7', 'shop_id': 3257646 }
        }).then(resp => {
            $scope.bill.shippingFee = resp.data.data.total;
            $scope.isLoading = false;
        }).catch(error => {
            $scope.isLoading = false;
            console.log(error);
        })
    }

    //submitOrder
    $scope.submitOrder = async function () {
        $scope.check = 0;
        $scope.messageAddress = "";
        $scope.messagePhone = "";
        console.log($scope.address);

        var re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;

        if (!re.test($scope.user.phone)) {
            $scope.messagePhone = "Số điện thoại không hợp lệ";
            $scope.check = 1;
        }

        if ($scope.bill.address == "" || $scope.bill.address == undefined || $scope.address.ward == null) {
            $scope.messageAddress = "Thông tin địa chỉ chưa đầy đủ";
            $scope.check = 1;
        }
        if ($scope.check == 1) {
            return;
        }


        if ($scope.user.address == null || $scope.user.address == "") {
            await Swal.fire({
                title: 'Bạn có lưu địa chỉ này làm địa chỉ mặc định?',
                text: "",
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes!'
            }).then((result) => {
                if (result.isConfirmed) {
                    console.log($scope.address);
                    $scope.user.divisionId = $scope.address.division.ProvinceID;
                    $scope.user.divisionName = $scope.address.division.ProvinceName;
                    $scope.user.districtId = $scope.address.district.DistrictID;
                    $scope.user.districtName = $scope.address.district.DistrictName;
                    $scope.user.wardCode = $scope.address.ward.WardCode;
                    $scope.user.wardName = $scope.address.ward.WardName;
                    $scope.user.address = $scope.bill.address;
                    $http.put(`${API_USER}/update-address/${$scope.user.userId}`, $scope.user, {
                        headers: { 'Authorization': 'Bearer ' + $scope.authToken }
                    })
                        .then(resp => {
                            console.log(resp.data);
                            localStorage.setItem("user", JSON.stringify(resp.data));
                            $scope.isLoading = false;
                        })
                        .catch(error => {
                            console.log(error);
                            $scope.isLoading = false;
                        })
                }
            })
        }
        $scope.bill.phone = $scope.user.phone;
        var data = {
            bill: $scope.bill,
            cartDetails: $rootScope.cartDetailChecked
        }
        console.log(data);

        await Swal.fire({
            title: 'Bạn có đặt hàng không?',
            text: "",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes!'
        }).then((result) => {
            if (result.isConfirmed) {
                $scope.isLoading = true;

                if ($scope.bill.payments == 0) {
                    $http.post(`${API_BILL_USER}`, data)
                        .then(resp => {
                            console.log(resp.data);
                            toastMessage("success", "Tạo đơn hàng thành công!");
                            sessionStorage.removeItem('alert');
                            sessionStorage.setItem('alert', 'true');
                            window.location.href = '/frontend_Customer/index.html#!/dashboard';
                            // location.reload();
                            resetCart(data.cartDetails)
                            $scope.isLoading = false;
                        }).catch(error => {
                            toastMessage("error", "Tạo đơn hàng thất bại!");
                            $scope.isLoading = false;
                            console.log(error);
                        })
                } else {
                    var vnp_OrderInfo = 'thanh toan hoa don';
                    var orderType = 'other';
                    var language = 'vn';
                    var amount = $scope.totalMoney + $scope.bill.shippingFee;
                    var bankCode = '';
                    localStorage.setItem('billVNP', JSON.stringify(data));
                    $http.post(`${API_VNPAY}/send?vnp_OrderInfo=${vnp_OrderInfo}&ordertype=${orderType}&amount=${amount}&bankcode=&language=${language}`)
                        .then(resp => {
                            window.location.href = resp.data.value;
                            $scope.isLoading = false;

                        }).catch(error => {
                            $scope.isLoading = false;
                            console.log(error);
                        })
                }
            }
        })

    }

    function resetCart(listCart) {
        $rootScope.cartDetail.forEach(cart =>{
            listCart.forEach(cartBuy => {
                if (cart.cartDetailId == cartBuy.cartDetailId) {
                    $rootScope.cartDetail.splice(cart, 1);
                }
            })
        })
        sessionStorage.setItem('cartDetail',JSON.stringify($rootScope.cartDetail))
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

app.directive('convertToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                return parseInt(val, 10);
            });
            ngModel.$formatters.push(function (val) {
                return '' + val;
            });
        }
    };
});
