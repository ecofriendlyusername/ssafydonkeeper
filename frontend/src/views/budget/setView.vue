<template>
  <div>
    <h1>예산 설정 페이지</h1>
    
    <div class="budget">
      <label for="total_budget">전체 예산: </label><input type="number" name="total_budget" value="0">
    </div>

    <div v-for="(ca, idx) in cate" :key="idx" class="budget">
      <label :for="ca"></label>{{ ca }}예산: <input type="number" :name="ca" value="0">    
    </div>

    <button v-on:click="postData()">예산 저장하기</button>

  </div>
</template>

<script>
export default {
  data() {
    return {
      cate: [
      'cate1', 'cate2', 'cate3', 'cate4', 'cate5', 
      ]
    }
  },
  methods: {
    getData(){
      this.axios.get(process.env.VUE_APP_API_URL + `account-book/incomeclassification`)
      .then(res=>{
        console.log(res.data)
        this.cate = res.data
      })
    },

    postData() {
      this.axios.post(process.env.VUE_APP_API_URL + '/account-book/budget')
      .then((res) => {
        console.log(res.data);
        this.$router.push('/budget');
      })
      .catch((err) => {
        console.log(err);
        alert('입력하지 않은 예산이 있는지 확인해 주세요.')
      })
    }
  },
  mounted() {
    this.getData()
  },
}
</script>

<style>
  .budget {
    font-size: 24px;
    padding: 4px;
  }
</style>