<template>
  <div class="DivH">
    <h2 v-on:click="flag = !flag" v-if="flag">수입</h2>
    <h2 v-on:click="flag = !flag" v-else>지출</h2>
    <h4 v-on:click="flag = !flag">바꾸기</h4>
    <br>
    <label for="assetId">assetId: </label><div type="text" id="assetId" :value="AC.name"
      v-on:click="aModalFlag = !aModalFlag">{{ AC.name }}</div>

    <div class="modal" v-show="aModalFlag">
      <ul>
        <h4>소득 분류</h4>
        <li v-for="el in AC_data" :key="el.id" v-on:click="AC = el">
          {{ el.name }}
        </li>
      </ul>
    </div>

    <br><br>
    <label for="classificationId">classificationId: </label>
    <div type="text" id="classificationId" :value="flag ? IC.name : SC.name" v-on:click="cModalFlag = !cModalFlag">{{ flag ? IC.name : SC.name }}</div>

    <div class="modal" v-show="cModalFlag && !flag">
      <ul>
        <h4>지출 분류</h4>
        <li v-for="el in SC_data" :key="el.id" v-on:click="SC = el">
          {{ el.name }}
        </li>
      </ul>
    </div>

    <div class="modal" v-show="cModalFlag && flag">
      <ul>
        <h4>수입 분류</h4>
        <li v-for="el in IC_data" :key="el.id" v-on:click="IC = el">
          {{ el.name }}
        </li>
      </ul>
    </div>
    <br><br>
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
      flag: false,
      aModalFlag: false,
      cModalFlag: false,
      IC: { name: '입력하기' },
      IC_data: [],
      SC: { name: '입력하기' },
      SC_data: [],
      AC: { name: '입력하기' },
      AC_data: [],
    }
  },

  methods: {
    getData() {
      // 소득 분류 가져오기
      this.axios.get(process.env.VUE_APP_API_URL + `/account-book/incomeclassification`)
        .then((res) => {
          this.IC_data = res.data
        })
        .catch((err) => { console.log(err) })
      // 소비 분류 가져오기
      this.axios.get(process.env.VUE_APP_API_URL + `/account-book/spendingclassification`)
        .then((res) => {
          this.SC_data = res.data
        })
        .catch((err) => { console.log(err) })
      // 자산 분류 가져오기
      this.axios.get(process.env.VUE_APP_API_URL + `/account-book/asset`)
        .then((res) => {
          this.AC_data = res.data
        })
        .catch((err) => { console.log(err) })

    },

    postData() {
      if (this.flag) {
        this.incomeadd()
      } else {
        this.spendadd()
      }

    },
    incomeadd() {
      this.axios({
        method: 'post',
        url: process.env.VUE_APP_API_URL + `/account-book/income`,
        data: {
          "assetId": this.AC.id,
          "classificationId": this.IC.id,
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
      this.axios({
        method: 'post',
        url: process.env.VUE_APP_API_URL + `/account-book/spending`,
        data: {
          "assetId": this.AC.id,
          "classificationId": this.SC.id,
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