<template>
  <headerComponent />
  <a
    href="https://kauth.kakao.com/oauth/authorize?client_id=f1433e701d3db7dd3776547238c0abac&redirect_uri=http://localhost:3000/kakaoCallback&response_type=code"><img
      src="./assets/kakao_login_medium_narrow.png" alt=""></a>

  <div @click="check">세션 체크</div>
  <br>
  <div @click="add">add</div>
  <br>
  <div @click="get">get</div>
  <br>  
  <div @click="monthget">monthget</div>
  <br>  
  <div @click="detailget">detailget</div>
  <br>
  <div @click="amountget">amountget</div>
  <br>
  <div @click="patch">patch</div>
  <br>
  <div @click="delet">delet</div>

  <router-view />
  <footerComponent />
</template>

<script>
import axios from 'axios'
axios.defaults.withCredentials = true; // 백엔드에서 cookie 세팅 할 수 있으려면 axios에 기본으로 되어있어야 한다. axios 한곳으로 몰아서 쓸거면 거기에 이거 작성해주세요.
import headerComponent from "@/components/headerComponent.vue";
import footerComponent from "@/components/footerComponent.vue";

export default {
  components: {
    headerComponent,
    footerComponent
  },

  methods: {
    check() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/auth/session`,
      })
        .then((res) => {
          console.log("세션")
          console.log(res.data)
        })
    },
    add() {
      axios({
        method: 'post',
        url: `http://localhost:8080/api/account-book/spending`,
        headers: {
          'Content-Type': 'application/json'
        },
        data: {
          "assetId" : 20,
          "spendingClassificationId" : 2,
          "date" : "2023-05-04",
          "amount" : 27000, 
          "detail": "엽닭",
          "memo": "승현이랑"
        },
      })
        .then((res) => {
          console.log("add")
          console.log(res.data)
        })
    },
    get() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending?page=0&size=10`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    monthget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending/2023/5?page=0&size=10`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    detailget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending/47`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    amountget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending/amount/2023/5`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    patch() {
      axios({
        method: 'patch',
        url: `http://localhost:8080/api/account-book/spending/47`,
        data: {
          "assetId" : 20,
          "spendingClassificationId" : 2,
          "date" : "2023-05-04",
          "amount" : 54000, 
          "detail": "엽닭",
          "memo": "민지랑승현이랑"
        },
      })
        .then((res) => {
          console.log("patch")
          console.log(res.data)
        })
    },
    delet() {
      axios({
        method: 'delete',
        url: `http://localhost:8080/api/account-book/spending/48`
      })
        .then((res) => {
          console.log("delete")
          console.log(res.data)
        })
    },
  },

  
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}
</style>
