<template>
  <div>
      <h1>나의 최근 소비 패턴</h1>
    <div>
      차트이미지
    </div>

    <RecomView/>
    

  </div>
</template>

<script>
import RecomView from './recomView.vue'

export default {
  components:{
    RecomView
  }

}
</script>

<style>


</style>