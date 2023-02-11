const app = angular.module("myApp", ["ngRoute"]);
app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "./layout/index-2.html"
        })
        .when("/index", {
            templateUrl: "./layout/index-2.html"
        })
        .when("/product", {
            templateUrl: "./layout/product-index.html",
            controller: "product_controller"
        })
        .when("/product/:id/:productName", {
            templateUrl: "./layout/product-detail.html",
            controller: "detail_controller"
        })
        .when("/cart", {
            templateUrl: "./layout/cart.html",
            controller: "cart-controller"
        })
        .when("/checkout", {
            templateUrl: "./layout/checkout.html",
            controller: "checkout-ctrl"
        })
        .when("/about", {
            templateUrl: "./layout/about.html"
        })
        .when("/contact", {
            templateUrl: "./layout/contact.html"
        })
        .when("/blog", {
            templateUrl: "./layout/blog.html"
        })
        .when("/faq", {
            templateUrl: "faq.html"
        })
        .when("/dashboard", {
            templateUrl: "./dashboard.html",
            controller: "dashboard-ctrl"
        })
        .when("/bill", {
            templateUrl: "./layout/bill.html",
            controller: "bill-ctrl"
        })
        .when("/buymore", {
            templateUrl: "./layout/buy-more.html",
            controller: "bill-ctrl"
        })
        .when("/productdetail-buymore/:id", {
            templateUrl: "./layout/product-detail-buy-more.html",
            controller: "bill-ctrl"
        })
        .when("/return-policy", {
            templateUrl: "./layout/return-policy.html"
        })

});
function myController($scope, $rootScope, $http) {
    const apiProductVariant = `http://localhost:8080/user/rest/product-variants`;
    const API_IMAGE = 'http://localhost:8080/admin/rest/image';
    const API_BILL_USER = "http://localhost:8080/user/rest/bills"


    // if (JSON.parse(localStorage.getItem('billVNP')) != 0) {

    var url_string = window.location.href;
    var url = new URL(url_string);
    console.log(url);
    var vnp_TransactionStatus = url.searchParams.get("vnp_TransactionStatus");
    console.log(vnp_TransactionStatus);
    var bill = JSON.parse(localStorage.getItem('billVNP'));
    console.log(bill);
    if (vnp_TransactionStatus == 00 && bill != null) {
        $http.post(`${API_BILL_USER}`, bill)
            .then(resp => {
                console.log(resp.data);
                toastMessage("success", "Tạo đơn hàng thành công!");
                sessionStorage.removeItem('alert');
                sessionStorage.setItem('alert', 'true');
                window.location.href = '/frontend_Customer/index.html#!/dashboard'
                $scope.isLoading = false;
            }).catch(error => {
                $scope.isLoading = false;
                console.log(error);
            })
        // localStorage.setItem('billVNP', 0);
    }
    // }


    $rootScope.user = sessionStorage.authToken2;
    $rootScope.subTotal = 0;
    if (sessionStorage.cartDetail != null) {
        $rootScope.cartDetail = JSON.parse(sessionStorage.cartDetail)
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
    }
    else {
        $rootScope.cartDetail = []
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

    $scope.logout = function () {
        Swal.fire({
            title: "Bạn có muốn đăng xuất ?",
            // text: "Sign out !",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes !'
        }).then((result) => {
            if (result.isConfirmed) {
                sessionStorage.clear()
                $rootScope.user = null;
                $rootScope.subTotal = 0;
                $rootScope.cartDetail = [];
                window.location.href = "#!index.html"
                document.getElementById('logo').click();
            }
        })
    }


    $rootScope.showOtp = 0;
    $scope.signinModal = function () {
        $rootScope.showOtp = 0;
        $('#signin-modal').modal('show');
    };


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
}
app.controller("my_controller", myController)

