<template>
  <div class="DivH">
    <form action="">
      
      수입<input type="radio" name="label" id="수입" >
      지출<input type="radio" name="label" id="지출">
      기타<input type="radio" name="label" id="기타">
      <br>

      <label for="날짜">날짜</label><input type="date" name="날짜" id=""><br>
      <label for="자산">자산</label><input type="text" name="자산" id=""><br>
      <label for="분류" v-on:click="isCategory = true">분류</label><input type="text" name="분류" id=""><br>
      <label for="금액">금액</label><input type="text" name="금액" id=""><br>
      <label for="내용">내용</label><input type="text" name="내용" id=""><br>

      <button type="submit">저장하기</button>
    </form>
  </div>
</template>

<script>
export default {
  data(){
    return {
      isCategory: false,
    }
  },

}
</script>

<style>
.DivH{
  min-height: 530px;
}

</style>