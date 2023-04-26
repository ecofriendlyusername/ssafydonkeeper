<template>
  <div class="header">
    돈키퍼
    <!-- 현재 페이지 {{ title }} -->
    <button v-on:click="this.$router.go(-1)">back</button>  
  </div>
  
</template>

<script>
export default {
    name: 'headerComponent',
    computed:{
        title() {
            return this.$store.state.title
        }
    }
}
</script>

<style>
.header {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  padding: 5px 30px;
  background-color: #4D82E6;
  color: white;
  border-bottom: rgb(220, 220, 220) solid 0.1px;
  font-size: 20px;
  font-weight: bold;
}
</style>