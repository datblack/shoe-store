app.controller('product_controller', function ($scope, $http, $rootScope) {
    $scope.products = [];
    $rootScope.id = {};
    var priceMin = 0;
    var priceMax = 0;
    const API_PRODUCT = "http://localhost:8080/user/rest/products";
    const API_OPTION_VALUE = "http://localhost:8080/user/rest/option-values";
    const API_IMAGE = 'http://localhost:8080/admin/rest/image';
    const API_PRODUCT_VARIANT = 'http://localhost:8080/user/rest/product-variants';
    const API_OPTION = 'http://localhost:8080/user/rest/options';

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
})  