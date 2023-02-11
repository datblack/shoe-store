app.controller('dashboard-ctrl', function ($scope, $http, $routeParams, $rootScope) {
    //getToken
    $scope.authToken = localStorage.getItem("authToken");
    if ($scope.authToken == null || $scope.authToken == undefined) {
        setTimeout(() => {
            document.location = '/admin#!/login';
        }, 2000);
        sweetError('Please login !');
        return;
    }

    $scope.bill = {};
    $scope.bills = [];
    $scope.billsNotReturn = [];
    $scope.lstSex = [{ id: 1, name: "Nam" }, { id: 0, name: "Nữ" }];

    console.log(sessionStorage.getItem("alert"));
    if (sessionStorage.getItem("alert") != undefined || sessionStorage.getItem("alert") != null) {
        toastMessage("", "Tạo đơn hàng thành công!", "success");
        sessionStorage.removeItem("alert");
    }

    const API_BILL = "http://localhost:8080/user/rest/bills";
    const API_BILL_DETAIL = "http://localhost:8080/user/rest/billdetails";
    const API_VARIANT_VALUE = 'http://localhost:8080/admin/rest/variant-values';
    const API_IMAGE = 'http://localhost:8080/admin/rest/image';
    const API_USER = 'http://localhost:8080/user/rest/customer';

    $scope.user = JSON.parse(localStorage.getItem("user"));
    $scope.userUpdate = {};
    $scope.userSex = {};
    $scope.show = 2;
    console.log($rootScope.showTab);

    async function load() {
        if ($rootScope.showTab != undefined) {
            $scope.show = $rootScope.showTab;
            if ($scope.show == 1) {

            } else if ($scope.show == 2) {
                $("#btnOrdersTab").click();
            } else if ($scope.show == 3) {
                $scope.messageError = {};
                await getUserUpdate($scope.user.userId);
                var index = await $scope.lstSex.findIndex(c => c.id == $scope.userUpdate.sex);
                $scope.userSex = angular.copy($scope.lstSex[index]);
                $("#btnAccountDetail").click();
                await $scope.getDivision();
            }
        } else {
            $("#btnOrdersTab").click();
        }
    }
    load();

    //changePasswordTab
    $scope.changePasswordTab = function () {
        $scope.show = 1;
    }

    // clickOrdersTab
    $scope.ordersTab = function () {
        if ($scope.show != 2) {
            $scope.show = 2;
            $scope.clickAll();
        }
    }

    //accountDetailTab
    $scope.accountDetailTab = async function () {
        if ($scope.show != 3) {
            $scope.show = 3;
            $scope.messageError = {};
            await getUserUpdate($scope.user.userId);
            var index = await $scope.lstSex.findIndex(c => c.id == $scope.userUpdate.sex);
            $scope.userSex = angular.copy($scope.lstSex[index]);
            await $scope.getDivision();
        }
    }

    async function getUserUpdate(id) {
        $scope.userUpdate = {};
        $scope.division = {};
        $scope.district = {};
        $scope.ward = {};
        $scope.isLoading = true;
        await $http.get(`${API_USER}/${id}`, {
            headers: { 'Authorization': 'Bearer ' + $scope.authToken }
        })
            .then(resp => {
                console.log(resp.data);
                $scope.userUpdate = angular.copy(resp.data);
                $scope.isLoading = false;
            })
            .catch(error => {
                console.log(error);
                $scope.isLoading = false;
            })
    }

    $scope.getBills = async function () {
        var dateNow = new Date(Date.now());
        $scope.isLoading = true;
        if ($scope.user != null) {
            await $http.get(`${API_BILL}/find-not-eligible-for-return-by-customer/${$scope.user.userId}`, {
                headers: { 'Authorization': 'Bearer ' + $scope.authToken }
            })
                .then(resp => {
                    console.log(resp.data);
                    $scope.billsNotReturn = resp.data;
                    $scope.isLoading = false;
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })

            $scope.isLoading = true;
            await $http.get(`${API_BILL}/${$scope.user.userId}`, {
                headers: { 'Authorization': 'Bearer ' + $scope.authToken }
            })
                .then(resp => {
                    // $scope.billsNotReturn.forEach(re => {
                    //     resp.data.forEach(b => {
                    //         if (b.billId == re.billId) {
                    //             b.checkReturn = true;
                    //         }
                    //     });
                    // });

                    resp.data.forEach(b => {
                        b.checkReturn = false;
                        var successDate = new Date(b.successDate);
                        var Difference_In_Time = dateNow.getTime() - successDate.getTime();
                        var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
                        b.differentDate = Difference_In_Days;
                        $scope.billsNotReturn.forEach(re => {
                            if (b.billId == re.billId) {
                                b.checkReturn = true;
                            }
                        });
                    });

                    $scope.lstTotalBill = resp.data;
                    console.log(resp.data);
                    $scope.bills = angular.copy($scope.lstTotalBill);
                    $scope.isLoading = false;
                })
                .catch(error => {
                    console.log(error);
                    $scope.isLoading = false;
                })
        }
    }
    $scope.getBills();

    var statusCheck = true;

    $scope.clickAll = function () {
        statusCheck = true;
        $scope.getBills();
    }

    $scope.status1 = function () {
        statusCheck = true;
        $scope.bills = [];
        $scope.lstTotalBill.forEach(b => {
            if (b.status == 1) {
                $scope.bills.push(b);
            }
        });
    }

    $scope.status2 = function () {
        statusCheck = true;
        $scope.bills = [];
        $scope.lstTotalBill.forEach(b => {
            if (b.status == 2) {
                $scope.bills.push(b);
            }
        });
    }

    $scope.status3 = function () {
        statusCheck = true;
        $scope.bills = [];
        $scope.lstTotalBill.forEach(b => {
            if (b.status == 3) {
                $scope.bills.push(b);
            }
        });
    }

    $scope.status4 = function () {
        statusCheck = true;
        $scope.bills = [];
        $scope.lstTotalBill.forEach(b => {
            if (b.status == 4) {
                $scope.bills.push(b);
            }
        });
    }

    $scope.status5 = function () {
        statusCheck = true;
        $scope.bills = [];
        $scope.lstTotalBill.forEach(b => {
            if (b.status == 5) {
                $scope.bills.push(b);
            }
        });
    }

    $scope.returnBill = function () {
        statusCheck = false;
        var dateNow = new Date(Date.now());
        $scope.isLoading = true;
        $http.get(`${API_BILL}/find-bill-return-by-user/${$scope.user.userId}`, {
            headers: { 'Authorization': 'Bearer ' + $scope.authToken }
        })
            .then(resp => {
                resp.data.forEach(b => {
                    b.checkReturn = true;
                    var successDate = new Date(b.successDate);
                    var Difference_In_Time = dateNow.getTime() - successDate.getTime();
                    var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
                    b.differentDate = Difference_In_Days;
                });
                console.log(resp.data);

                // $scope.lstTotalBill = resp.data;
                $scope.bills = angular.copy(resp.data);
                $scope.isLoading = false;
            })
            .catch(error => {
                console.log(error);
                $scope.isLoading = false;
            })
        // $scope.bills = [];
        // $scope.lstTotalBill.forEach(b => {
        //     b.billDetails.forEach(de => {
        //         if (de.status != 0) {
        //             console.log(b);
        //             $scope.bills.push(b);
        //             return;
        //         }
        //     });
        // });
    }


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

    //getBillDetail
    $scope.getBill = function (bill) {
        $rootScope.bill = angular.copy(bill);
        localStorage.setItem("bill", JSON.stringify(bill));
        localStorage.setItem("statusCheck", JSON.stringify(statusCheck));
        window.location.href = '/frontend_Customer/index.html#!/bill'
    }

    //returnProduct
    $scope.returnProduct = async function (bill) {
        $('#modalReturnProduct').modal('show');
        $scope.selectedAllBillDetail = false;
        $scope.listMessage = [];
        $scope.isLoading = true;
        $scope.bill = bill;
        $http.get(`${API_BILL_DETAIL}/${$scope.bill.billId}`, {
            headers: { 'Authorization': 'Bearer ' + $scope.authToken }
        })
            .then(resp => {
                $scope.bill.billDetails = resp.data.filter(function (de) {
                    return de.status == 0;
                });
                getVariantValues($scope.bill.billDetails);
                $scope.bill.billDetails.forEach(item => {
                    var detailReturnSelect = 'detailReturnSelect' + item.detailBillId;
                    $scope[detailReturnSelect] = {
                        quantityReturn: 1,
                        note: ''
                    };
                    // $scope.bill.billDetails.forEach(de => {
                    //     $http.get(`${API_IMAGE}/find-by-product/${de.productVariants.variantId}`, {
                    //         headers: { 'Authorization': 'Bearer ' + $scope.authToken }
                    //     })
                    //         .then(resp => {
                    //             de.image = resp.data[0];
                    //         })
                    //         .catch(error => {
                    //             console.log(error);
                    //             $scope.isLoading = false;
                    //         })
                });
                console.log($scope.bill.billDetails);
                $scope.isLoading = false;
            })
            .catch(error => {
                console.log(error);
                $scope.isLoading = false;
            })
    }

    //clickSelectAllBillDetail
    $scope.clickSelectAllBillDetail = function (value) {
        var checkedValue = document.getElementsByName('billDetailcheckbox');
        if (value == false) {
            for (let i = 0; i < checkedValue.length; i++) {
                checkedValue[i].checked = true;
            }
        } else {
            for (let i = 0; i < checkedValue.length; i++) {
                checkedValue[i].checked = false;
            }
        }
    }

    //Tạo dynamicQuantityReturn
    $scope.dynamicQuantityReturn = function (id) {
        detailReturnSelect = 'detailReturnSelect' + id;
        return $scope[detailReturnSelect];
    }

    //Click trả hàng
    $scope.btnReturn = async function () {
        $scope.idBillDetailChecked = [];
        $scope.listProductReturns = [];
        $scope.listMessage = [];
        var checkedValue = document.getElementsByName('billDetailcheckbox');
        console.log(checkedValue.length);
        for (let i = 0; i < checkedValue.length; i++) {
            if (checkedValue[i].checked == true) {
                detailReturnSelect = 'detailReturnSelect' + checkedValue[i].value;
                var quantityReturn = $scope[detailReturnSelect].quantityReturn;
                var note = $scope[detailReturnSelect].note;
                console.log($scope.bill.billDetails);
                console.log(i);
                var quantity = $scope.bill.billDetails[i].quantity
                var itemMessage = {
                    index: i,
                    quantity: '',
                    note: ''
                };
                if (quantityReturn > quantity || quantityReturn < 0) {
                    itemMessage.quantity = 'Số lượng không phù hợp !';
                    $scope.listMessage[i] = angular.copy(itemMessage);
                } else if (isNaN(quantityReturn)) {
                    itemMessage.quantity = 'Số lượng trả chưa đúng định dạng !';
                    $scope.listMessage[i] = angular.copy(itemMessage);
                } else if (note.length > 255 || note.length == 0) {
                    itemMessage.note = 'Ghi chú phải nhiều hơn 0 ký tự và ít hơn 255 ký tự !';
                    $scope.listMessage[i] = angular.copy(itemMessage);
                }
                $scope.idBillDetailChecked.push(checkedValue[i].value)
            }
        }
        if ($scope.idBillDetailChecked.length == 0) {
            toastMessage('', 'Mời bạn chọn sản phẩm muốn trả !', 'error')
            return;
        }
        if ($scope.listMessage.length > 0) {
            return;
        }

        $scope.idBillDetailChecked.forEach(id => {
            var detailReturnSelect = 'detailReturnSelect' + id;
            var quantityReturn = $scope[detailReturnSelect].quantityReturn;
            var note = $scope[detailReturnSelect].note;
            var index = $scope.bill.billDetails.findIndex(o => o.detailBillId == id);
            var detailReturn = angular.copy($scope.bill.billDetails[index]);
            detailReturn.quantity = quantityReturn;
            detailReturn.note = note;
            $scope.listProductReturns.push(detailReturn);
        });


        await Swal.fire({
            title: 'Bạn có chắc chắn muốn trả hàng?',
            // text: "You won't be to change this!",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes'
        }).then((result) => {
            if (result.isConfirmed) {
                console.log($scope.listProductReturns);
                // $scope.isLoadingModal = true;
                $http.put(`${API_BILL_DETAIL}/return-product`, $scope.listProductReturns, {
                    headers: { 'Authorization': 'Bearer ' + $scope.authToken }
                })
                    .then(resp => {
                        $scope.isLoading = false;
                        console.log(resp.data);
                        $('#modalReturnProduct').modal('hide');
                        toastMessage('', 'Xác nhận trả hàng thành công !', 'success');

                        var index = $scope.bills.findIndex(c => c.billId == $scope.bill.billId);
                        $scope.bills[index].checkReturn = true;
                        $scope.lstTotalBill[index].checkReturn = true;
                      
                    })
                    .catch(error => {
                        console.log(error);
                        toastMessage('', 'Trả hàng thất bại !', 'error');
                        $scope.isLoading = false;
                    })
            }
        })
    }

    //hủy đơn hàng 
    $scope.cancelBill = async function (bill) {
        await Swal.fire({
            title: 'Bạn có chắc chắn muốn hủy đơn?',
            // text: "You won't be to change this!",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes'
        }).then((result) => {
            if (result.isConfirmed) {
                $scope.isLoading = true;
                $http.put(`${API_BILL}/cancel-bill`, bill, {
                    headers: { 'Authorization': 'Bearer ' + $scope.authToken }
                })
                    .then(resp => {
                        $scope.isLoading = false;
                        console.log(resp.data);
                        var index = $scope.bills.findIndex(c => c.billId == bill.billId);
                        $scope.bills[index].status = 5;
                        $scope.lstTotalBill[index].status = 5;
                        toastMessage('', 'Hủy đơn thành công !', 'success');
                    })
                    .catch(error => {
                        console.log(error);
                        toastMessage('', 'Hủy đơn thất bại !', 'error');
                        $scope.isLoading = false;
                    })
            }
        })
    }


    $scope.division = {};
    $scope.district = {};
    $scope.ward = {};
    $scope.getDivision = function () {
        $scope.lstDivision = [];
        $http.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/province', {
            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
        })
            .then(resp => {
                $scope.lstDivision = resp.data.data;
                var index = $scope.lstDivision.findIndex(c => c.ProvinceID == $scope.userUpdate.divisionId);
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
                        var index = $scope.lstDistrict.findIndex(c => c.DistrictID == $scope.userUpdate.districtId);
                        $scope.district = $scope.lstDistrict[index];

                        var data = {
                            district_id: parseInt($scope.district.DistrictID)
                        }

                        $http.post(`https://online-gateway.ghn.vn/shiip/public-api/master-data/ward`, data, {
                            headers: { 'token': 'd5ce5c1b-343d-11ed-b824-262f869eb1a7' }
                        })
                            .then(resp => {
                                $scope.lstWard = resp.data.data;
                                var index = $scope.lstWard.findIndex(c => c.WardCode == $scope.userUpdate.wardCode);
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


    $scope.changeDivision = function (division) {
        console.log(division);
        if (division != null) {
            $scope.messageDivision = '';
            $scope.lstDistrict = [];
            $scope.lstWard = [];
            $scope.userUpdate.divisionId = division.ProvinceID;
            $scope.userUpdate.divisionName = division.ProvinceName;
            $scope.userUpdate.districtId = 0;
            $scope.userUpdate.districtName = null;
            $scope.userUpdate.wardCode = null;
            $scope.userUpdate.wardName = null;
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
    }

    $scope.changeDistrict = function (district) {
        if (district != null) {
            $scope.messageDistrict = '';
            $scope.lstWard = [];
            $scope.userUpdate.districtId = district.DistrictID;
            $scope.userUpdate.districtName = district.DistrictName;
            $scope.userUpdate.wardCode = null;
            $scope.userUpdate.wardName = null;
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
            $scope.userUpdate.wardName = ward.WardName;
            $scope.userUpdate.wardCode = ward.WardCode;
        }
    }

    // update thông tin khách hàng
    $scope.updateUser = function () {
        console.log($scope.userUpdate);
        $scope.messageError = {};
        $scope.isLoading = true;
        $http.put(`${API_USER}/update-profile/${$scope.userUpdate.userId}`, $scope.userUpdate, {
            headers: { 'Authorization': 'Bearer ' + $scope.authToken }
        })
            .then(resp => {
                $scope.isLoading = false;
                console.log(resp.data);
                toastMessage('', "Cập nhật thành công !", 'success');
                localStorage.setItem("user", JSON.stringify(resp.data));
                sessionStorage.setItem("authToken2", JSON.stringify(resp.data));
            })
            .catch(error => {
                console.log(error);
                $scope.messageError = error.data;
                toastMessage('', "Cập nhật thất bại !", 'error');
                $scope.isLoading = false;
            })
    }

    //change sex
    $scope.changeSex = function (sex) {
        console.log(sex);
        $scope.userUpdate.sex = sex.id;
    }

    //Thông báo
    function toastMessage(heading, text, icon) {
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({
            icon: icon,
            title: text
        })
    };

    function sweetError(title) {
        Swal.fire(
            title,
            '',
            'error'
        )
    };

})