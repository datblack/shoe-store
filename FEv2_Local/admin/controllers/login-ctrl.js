app.controller("login-ctrl", function ($scope, $http, $rootScope) {

    $scope.form = {
        username: '',
        password: ''
    }
    localStorage.clear();
    $scope.btnLogin = function () {
        $scope.isLoading = true;
        $http.post(`http://localhost:8080/auth/admin/login`, $scope.form)
            .then(resp => {
                localStorage.clear();
                $scope.isLoading = false;
                // console.log(resp.data);
                // localStorage.setItem("user", resp.data);

                $scope.messageError = {};
                // if (resp.data.user.role < 2) {
                //     toastMessage('', 'You are not allow to access !', 'error')
                //     return;
                // }
                if (resp.data != null) {

                    localStorage.setItem("authToken2", JSON.stringify(resp.data.user));
                    localStorage.setItem('setupTime', 24 * 60 * 60 * 1000);
                    localStorage.setItem("authToken", resp.data.accessToken);
                    if (resp.data.user.role == 3) {
                        document.location = '/admin#!';
                    } else {
                        document.location = '/admin#!/order';
                    }

                } else {
                    toastMessage('', 'Email hoặc mật khẩu sai', 'error')
                }
            })
            .catch(error => {
                $scope.isLoading = false;
                console.log(error);
                $scope.messageError = error.data;
                toastMessage('', 'Email hoặc mật khẩu sai', 'error')
            })
    }



    function toastMessage(heading, text, icon) {
        // $.toast({
        //     heading: heading,
        //     text: text,
        //     position: 'top-right',
        //     icon: icon
        // })
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
    }
});