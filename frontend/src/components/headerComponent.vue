<template>
  <div class="header">
    현재 페이지 {{ title }}
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
  padding: 0 30px;
  background-color: #ff9bc2;
  border-bottom: rgb(220, 220, 220) solid 0.1px;
}
</style>