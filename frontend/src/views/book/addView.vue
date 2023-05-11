<template>
  <div class="DivH">
    {{ income }}
    <div v-on:click="income = true">수입</div> <div v-on:click="income=false">지출</div>
    <br>
    <label for="assetId">assetId: </label><input type="text" id="assetId"><br><br>
    <label for="incomeClassificationId">incomeClassificationId: </label><input type="text" id="incomeClassificationId"><br><br>
    <label for="date">date: </label><input type="date" id="date"><br><br>
    <label for="amount">amount: </label><input type="number" id="amount"><br><br>
    <label for="detail">detail: </label><input type="text" id="detail"><br><br>
    <label for="memo">memo: </label><input type="text" id="memo"><br><br>
    <button v-on:click=add()>저장하기</button>
  </div>
  
</template>

<script>

export default {
  data() {
    return {
      income: true,
    }
  },
  methods: {
    getData(){
      this.axios.get(process.env.VUE_APP_API_URL + ``)
    },

    add(){
      if (this.income) {
        this.spendadd()
      }else{
        this.incomeadd()
      }
    },
    incomeadd() {
      console.log(document.querySelector("#assetId").value)
      console.log(document.querySelector("#incomeClassificationId").value)
      console.log(document.querySelector("#date").value)
      console.log(document.querySelector("#amount").value)
      console.log(document.querySelector("#detail").value)
      console.log(document.querySelector("#memo").value)

      this.axios({
        method: 'post',
        url: process.env.VUE_APP_API_URL + `/account-book/income`,
        data: {
          "assetId" : document.querySelector("#assetId").value,
          "incomeClassificationId" : document.querySelector("#incomeClassificationId").value,
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

    spendadd() {
      console.log(document.querySelector("#assetId").value)
      console.log(document.querySelector("#incomeClassificationId").value)
      console.log(document.querySelector("#date").value)
      console.log(document.querySelector("#amount").value)
      console.log(document.querySelector("#detail").value)
      console.log(document.querySelector("#memo").value)

      this.axios({
        method: 'post',
        url: process.env.VUE_APP_API_URL + `/account-book/spending`,
        data: {
          "assetId" : document.querySelector("#assetId").value,
          "spendingClassificationId" : document.querySelector("#incomeClassificationId").value,
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
    // this.getData()
  },

}
</script>

<style>
.DivH {
  min-height: 530px;
}
</style>