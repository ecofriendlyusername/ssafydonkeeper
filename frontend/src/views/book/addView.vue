<template>
  <div class="DivH">
    {{ income }}
    <div v-on:click="income = true" v-if="!income">수입</div>
    <div v-on:click="income = false" v-else>지출</div>
    <br>
    <label for="assetId">assetId: </label><input type="text" id="assetId"><br><br>
    <label for="classificationId">classificationId: </label><input type="text"
      id="classificationId"><br><br>
    <label for="date">date: </label><input type="date" id="date"><br><br>
    <label for="amount">amount: </label><input type="number" id="amount"><br><br>
    <label for="detail">detail: </label><input type="text" id="detail"><br><br>
    <label for="memo">memo: </label><input type="text" id="memo"><br><br>
    <button v-on:click=postData()>저장하기</button>
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
    getData() {
      // 소득 분류 가져오기
      this.axios.get(process.env.VUE_APP_API_URL + `/account-book/incomeclassification`)
        .then((res) => {
          console.log(res.data)
        })
        .catch((err) => {console.log(err)})
      // 소비 분류 가져오기
      this.axios.get(process.env.VUE_APP_API_URL + `/account-book/spendingclassification`)
        .then((res) => {
          console.log(res.data)
        })
        .catch((err) => {console.log(err)})
      // 자산 분류 가져오기
        this.axios.get(process.env.VUE_APP_API_URL + `/account-book/asset`)
        .then((res) => {
          console.log(res.data)
        })
        .catch((err) => {console.log(err)})
      
    },

    postData() {
      if (this.income) {
        this.spendadd()
      } else {
        this.incomeadd()
      }
    },
    incomeadd() {
      console.log(document.querySelector("#assetId").value)
      console.log(document.querySelector("#classificationId").value)
      console.log(document.querySelector("#date").value)
      console.log(document.querySelector("#amount").value)
      console.log(document.querySelector("#detail").value)
      console.log(document.querySelector("#memo").value)

      this.axios({
        method: 'post',
        url: process.env.VUE_APP_API_URL + `/account-book/income`,
        data: {
          "assetId": document.querySelector("#assetId").value,
          "classificationId": document.querySelector("#classificationId").value,
          "date": document.querySelector("#date").value,
          "amount": document.querySelector("#amount").value,
          "detail": document.querySelector("#detail").value,
          "memo": document.querySelector("#memo").value
        },
      })
        .then((res) => {
          console.log("add")
          console.log(res.data)
        })
        .catch(err => { console.log(err) })
    },

    spendadd() {
      console.log(document.querySelector("#assetId").value)
      console.log(document.querySelector("#classificationId").value)
      console.log(document.querySelector("#date").value)
      console.log(document.querySelector("#amount").value)
      console.log(document.querySelector("#detail").value)
      console.log(document.querySelector("#memo").value)

      this.axios({
        method: 'post',
        url: process.env.VUE_APP_API_URL + `/account-book/spending`,
        data: {
          "assetId": document.querySelector("#assetId").value,
          "classificationId": document.querySelector("#classificationId").value,
          "date": document.querySelector("#date").value,
          "amount": document.querySelector("#amount").value,
          "detail": document.querySelector("#detail").value,
          "memo": document.querySelector("#memo").value
        },
      })
        .then((res) => {
          console.log("add")
          console.log(res.data)
        })
        .catch(err => { console.log(err) })
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