<template>
  <div>
    <h2>2개의 챌린지를 진행중이에요.</h2>

    <h4>짠테크 챌린지</h4>
    <ul>전체보기</ul>

    <detailViewVue />


  </div>
</template>

<script>
import detailViewVue from './detailView.vue'


export default {
  components: {
    detailViewVue
  }

}
</script>

<style></style>