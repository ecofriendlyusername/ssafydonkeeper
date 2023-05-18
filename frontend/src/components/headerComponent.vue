<template>
  <div class="header">
    <div v-on:click="this.$router.go(-1)" style="font-weight:bold; width:150px; display:flex;">◀</div>
    돈키퍼
    <!-- 현재 페이지 {{ title }} -->
    <!-- <div :click="logout()">logout</div> -->
  </div>
</template>

<script>
export default {
  name: 'headerComponent',
  computed: {
    title() {
      return this.$store.state.title
    }
  },
  methods: {
    // logout() {
    //   window.Kakao.Auth.logout()
    //     .then((res) => {
    //       console.log(res.data);
    //       console.log(Kakao.Auth.getAccessToken()); // null
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //       console.log('Not logged in.');
    //     });
    // }
  }
}
</script>

<style>
.header {
  display: flex;
  align-items: center;
  /* justify-content: center; */
  height: 40px;
  padding: 5px 30px;
  background-color: #4D82E6;
  color: white;
  border-bottom: rgb(220, 220, 220) solid 0.1px;
  font-size: 20px;
  font-weight: bold;
}
</style>