<template>
  <div class="DivH">
    <br>
    <label for="assetId">assetId: </label><input type="text" id="assetId"><br><br>
    <label for="incomeClassificationId">incomeClassificationId: </label><input type="text" id="incomeClassificationId"><br><br>
    <label for="date">date: </label><input type="date" id="date"><br><br>
    <label for="amount">amount: </label><input type="number" id="amount"><br><br>
    <label for="detail">detail: </label><input type="text" id="detail"><br><br>
    <label for="memo">memo: </label><input type="text" id="memo"><br><br>
    <button v-on:click=incomeadd()>저장하기</button>

  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      isCategory: false,
    }
  },
  methods: {
    getData(){
      axios.get()
    },
    incomeadd() {
      console.log(document.querySelector("#date").value)
      axios({
        method: 'post',
        url: process.env.VUE_APP_API_URL + `/account-book/income`,
        data: {
          "assetId" : 4,
          "incomeClassificationId" : 5,
          "date" : document.querySelector("#date").value,
          "amount" : document.querySelector("#amount").value, 
          "detail": document.querySelector("#detail").value,
          "memo": document.querySelector("#memo").value
        },
      })
      .then((res) => {
        console.log("add")
        console.log(res.data)
      })
      .catch(err=>{console.log(err)})
    },
  },
  mounted() {
    this.getData()
  },

}
</script>

<style>
.DivH {
  min-height: 530px;
}
</style>