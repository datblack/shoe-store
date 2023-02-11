app.controller('login_controller', function ($scope, $http, $routeParams, $rootScope) {

  $scope.form = {
    username: '',
    password: ''
  }

  $scope.formRegister = {
    fullName: '',
    sex: 0,
    email: '',
    phone: '',
    password: '',

  }
  $scope.otpInput = ''

  $scope.formRegister.sex = '0';
  $scope.showOtp = false;
  $scope.confirmOtp = true;
  $scope.expiredOTP = false;
  $scope.userRegister = {}

  const apiCartDetail = `http://localhost:8080/user/rest/cartdetail`;
  const apiLogin = `http://localhost:8080/auth/user/login`;
  const apiCart = `http://localhost:8080/user/rest/cart`;
  const API_REGISTER = 'http://localhost:8080/user/rest/customer';
  const API_RESET_OTP = 'http://localhost:8080/user/rest/customer/reset-otp';
  const API_CHECK_EMAIL = 'http://localhost:8080/user/rest/customer/check-email';

  $scope.btnLogin = function () {
    $http.post(apiLogin, $scope.form)
      .then(resp => {
        localStorage.clear();
        localStorage.setItem("user", JSON.stringify(resp.data.user));
        localStorage.setItem("authToken", resp.data.accessToken);
        sessionStorage.setItem("authToken2", JSON.stringify(resp.data.user));
        sessionStorage.setItem('setupTime', 24 * 60 * 60 * 1000);
        sessionStorage.setItem("authToken", resp.data.accessToken);
        $rootScope.user = resp.data.user;

        checkCart(resp.data.user.userId, resp.data.user);
        document.getElementById('close-login').click();
        toastMessage("success", "Login Success")
      }
      )
      .catch(error => {
        console.log(error);
        toastMessage("error", "Email hoặc mật khẩu không đúng!")
      })
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

  function getCartDetails(cartId) {
    $http.get(`${apiCartDetail}/find-by-cart/${cartId}`)
      .then(function (response) {
        sessionStorage.setItem('cartDetail', JSON.stringify(response.data))
        $rootScope.cartDetail = JSON.parse(sessionStorage.cartDetail)
        $rootScope.cartDetail.forEach(element => {
          $rootScope.subTotal += element.productVariants.price * element.quantity;
        });
      })
      .catch(function (error) {
        console.error(error);
      })
  }

  function checkCart(userId, user) {
    $http.get(`${apiCart}/find-by-user/${userId}`)
      .then(function (response) {
        if (response.data == "") {
          postCart(user)
        } else {
          sessionStorage.setItem('cart', JSON.stringify(response.data))
          getCartDetails(response.data[0].cartId)
        }
      })
      .catch(function (error) {
        console.log(error);
      })
  }

  function postCart(user) {
    param = {
      status: 0,
      users: user,
    };
    $http
      .post(apiCart, param)
      .then(function (response) {
        sessionStorage.setItem("cart", JSON.stringify(response.data));
        sessionStorage.setItem("cartDetail");
      })
      .catch(function (error) {
        console.error(error);
      });
  }


  // Dang ky khach hang

  $scope.user = {};
  $scope.users = [];
  $scope.index = -1;
  $scope.isLoading = false;
  //  const api = `http://localhost:8080/PRO2111_FALL2022/auth/admin/login`;
  console.log($rootScope.showOtp);
  $scope.isLoading = true;
  $scope.initializUser = async function () {
    await $http
      .get(`${api}`, {
        headers: { Authorization: "Bearer " + $scope.authToken },
      })
      .then(function (response) {
        console.log(response.data);
        $scope.users = angular.copy(response.data);
        $scope.isLoading = false;
      }).catch(function (error) {
        console.log(error);
        $scope.isLoading = false;
      });
  };

  $scope.register = function () {
    $http.get(`${API_CHECK_EMAIL}/` + $scope.formRegister.email)
      .then(res => {
        $http.post(`${API_REGISTER}`, $scope.formRegister)
          .then(resp => {
            console.log(resp.data);
            $scope.userRegister = resp.data;
            countTime();
          })
          .catch(error => {
            Swal.fire({
              icon: 'error',
              title: error.data.phone
            })
          })
      })
      .catch(error => {
        console.log(error);
        if (error.data.error == "Bad Request") {
          toastMessage('error','Dữ liệu không hợp lệ')
        } else {
          if (error.data.object.status == 2) {
            if (error.data != "" && error.data.object.status == 2) {
              Swal.fire({
                title: 'Tài khoản của bạn chưa được xác nhận !',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'XÁC NHẬN !'
              }).then((result) => {
                if (result.isConfirmed) {
                  $scope.userRegister.userId = error.data.object.userId;
                  $scope.showOtp = true;
                  $scope.resetOtp();
                }
              })
            }
          } else {
            Swal.fire({
              icon: 'error',
              title: error.data.message
            })
          }
        }
      })
  };

  function countTime() {
    setTimeout(() => {
      // Credit: Mateusz Rybczonec

      $scope.FULL_DASH_ARRAY = 283;
      $scope.WARNING_THRESHOLD = 10;
      $scope.ALERT_THRESHOLD = 5;

      $scope.COLOR_CODES = {
        info: {
          color: "green"
        },
        warning: {
          color: "orange",
          threshold: $scope.WARNING_THRESHOLD
        },
        alert: {
          color: "red",
          threshold: $scope.ALERT_THRESHOLD
        }
      };

      $scope.TIME_LIMIT = 120;
      $scope.timePassed = 0;
      $scope.timeLeft = $scope.TIME_LIMIT;
      $scope.timerInterval = null;
      $scope.remainingPathColor = $scope.COLOR_CODES.info.color;

      document.getElementById("app").innerHTML = `
  <div class="base-timer">
    <svg class="base-timer__svg" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
      <g class="base-timer__circle">
        <circle class="base-timer__path-elapsed" cx="50" cy="50" r="45"></circle>
        <path
          id="base-timer-path-remaining"
          stroke-dasharray="283"
          class="base-timer__path-remaining ${$scope.remainingPathColor}"
          d="
            M 50, 50
            m -45, 0
            a 45,45 0 1,0 90,0
            a 45,45 0 1,0 -90,0
          "
        ></path>
      </g>
    </svg>
    <span id="base-timer-label" class="base-timer__label">${formatTime(
        $scope.timeLeft
      )}</span>
  </div>
  `;

      startTimer();
    }, 500);
    $scope.showOtp = true;
  }

  $scope.backRegister = function () {
    $scope.showOtp = false;
    onTimesUp();
    $scope.expiredOTP = false;
    window.location.href = '#register'
  }

  $scope.confirmOpt = function (value) {
    $http.put(`${API_REGISTER}/${$scope.userRegister.userId}`, value)
      .then(resp => {
        console.log(resp.data);
        toastMessage("success", "Xác nhận thành công !")
        $scope.showOtp = false;
        onTimesUp();
        $scope.expiredOTP = false;
        document.getElementById('signin-tab').click()
      })
      .catch(error => {
        $scope.confirmOtp = false;
        console.log(error);
      })
  };



  function onTimesUp() {
    clearInterval($scope.timerInterval);
  }

  function startTimer() {
    $scope.timerInterval = setInterval(() => {
      $scope.timePassed = $scope.timePassed += 1;
      $scope.timeLeft = $scope.TIME_LIMIT - $scope.timePassed;
      document.getElementById("base-timer-label").innerHTML = formatTime(
        $scope.timeLeft
      );
      setCircleDasharray();
      setRemainingPathColor($scope.timeLeft);

      if ($scope.timeLeft === 0) {
        $scope.expiredOTP = true;
        onTimesUp();
        window.location.href = '#register'
      }
    }, 1000);
  }

  $scope.resetOtp = function () {
    $scope.expiredOTP = false;
    window.location.href = '#register'
    $http.put(`${API_RESET_OTP}/${$scope.userRegister.userId}`)
      .then(res => {
        countTime();
      })
      .catch(err => {
        console.log(err);
      })
  }

  function formatTime(time) {
    const minutes = Math.floor(time / 60);
    let seconds = time % 60;

    if (seconds < 10) {
      seconds = `0${seconds}`;
    }

    return `${minutes}:${seconds}`;
  }

  function setRemainingPathColor(timeLeft) {
    const { alert, warning, info } = $scope.COLOR_CODES;
    if (timeLeft <= alert.threshold) {
      document
        .getElementById("base-timer-path-remaining")
        .classList.remove(warning.color);
      document
        .getElementById("base-timer-path-remaining")
        .classList.add(alert.color);
    } else if (timeLeft <= warning.threshold) {
      document
        .getElementById("base-timer-path-remaining")
        .classList.remove(info.color);
      document
        .getElementById("base-timer-path-remaining")
        .classList.add(warning.color);
    }
  }

  function calculateTimeFraction() {
    const rawTimeFraction = $scope.timeLeft / $scope.TIME_LIMIT;
    return rawTimeFraction - (1 / $scope.TIME_LIMIT) * (1 - rawTimeFraction);
  }

  function setCircleDasharray() {
    const circleDasharray = `${(
      calculateTimeFraction() * $scope.FULL_DASH_ARRAY
    ).toFixed(0)} 283`;
    document
      .getElementById("base-timer-path-remaining")
      .setAttribute("stroke-dasharray", circleDasharray);
  }
});

