<template>
  <headerComponent/>
  <a href="https://kauth.kakao.com/oauth/authorize?client_id=f1433e701d3db7dd3776547238c0abac&redirect_uri=http://localhost:3000/kakaoCallback&response_type=code"><img src="./assets/kakao_login_medium_narrow.png" alt=""></a>
  
  <div @click = "check">세션 체크</div>
  <router-view />
  <footerComponent/>
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

  methods : {
    check() {
      axios({
            method: 'get',
            url: `http://localhost:8080/api/auth/session`,
          })
            .then((res) => {
              console.log("세션")
              console.log(res.data)
            })
    }
  }
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
