<template>
  <headerComponent />
  <div class="test" v-if="!loginCheck">
    <a :href="`https://kauth.kakao.com/oauth/authorize?client_id=f1433e701d3db7dd3776547238c0abac&redirect_uri=${kakaoUrl}/kakaoCallback&response_type=code`">
      <img src="./assets/kakao_login_medium_narrow.png" alt="">
    </a>
  </div>
    <div class="test" v-if="loginCheck">

      

    <div @click="check">세션 체크</div>

    <br>
    <div @click="incomeClassificationGet">incomeClassificationGet</div>
    
    <br>
    <div @click="addIncomeclassification">addIncomeclassification</div>

    <br>
    <div @click="updateIncomeClassification">updateIncomeClassification</div>
    
    <br>
    <div @click="deletIncomeClassification">deletIncomeClassification</div>

    <br>
    <div @click="SpendingClassificationadd">SpendingClassificationadd</div>

    <br>
    <div @click="SpendingClassificationGet">SpendingClassificationGet</div>
    
    <br>
    <div @click="updateSpendingClassification">updateSpendingClassification</div>
    
    <br>
    <div @click="deletSpendingClassification">deletSpendingClassification</div>
    
    <br>
    <div @click="AssetGet">AssetGet</div>

    <br>
    <div @click="budgetadd">budgetadd</div>

    <br>
    <div @click="budgetmonthget">budgetmonthget</div>

    <br>
    <div @click="budgetpatch">budgetpatch</div>

    <br>
    <div @click="budgetdelet">budgetdelet</div>

    <br>
    <div @click="getMonthTotalAmmount">getMonthTotalAmmount</div>

    <br>
    <div @click="getDateTotalAmmount">getDateTotalAmmount</div>

    <br>
    <div @click="get">get</div>
    <br>  
    <div @click="monthget">monthget</div>
    <br>  
    <div @click="detailget">detailget</div>
    <br>
    <div @click="amountget">amountget</div>
    <br>
    <div @click="patch">patch</div>
    <br>
    <div @click="delet">delet</div>
    <br>
    <br>
    <br>
    <div @click="incomeadd">incomeadd</div>
    <br>
    <div @click="incomegetall">incomegetall</div>
    
    <br>
    <div @click="incomemonthget">incomemonthget</div>
    
    <br>
    <div @click="incomedetailget">incomedetailget</div>
    <br>
    <div @click="incomepatch">incomepatch</div>

    <br>
    <div @click="incomedelet">incomedelet</div>
    </div>
  
  <router-view />
  <footerComponent />
</template>

<script>
import axios from 'axios'
axios.defaults.withCredentials = true; // 백엔드에서 cookie 세팅 할 수 있으려면 axios에 기본으로 되어있어야 한다. axios 한곳으로 몰아서 쓸거면 거기에 이거 작성해주세요.
import headerComponent from "@/components/headerComponent.vue";
import footerComponent from "@/components/footerComponent.vue";

export default {
  data() {
    return {
      kakaoUrl: process.env.VUE_APP_URL
    }
  },
  components: {
    headerComponent,
    footerComponent
  },
  computed: {
    loginCheck() {
      return this.$store.state.loginCheck
    }
  },

  methods: {
    updateIncomeClassification() {
      axios({
        method: 'patch',
        url: `http://localhost:8080/api/account-book/incomeclassification/66`,
        data: {
          "name" : "엄마",
        }
      })
        .then((res) => {
          console.log("updateIncomeClassification")
          console.log(res.data)
        })
      

    },
    deletIncomeClassification() {
      axios({
        method: 'delete',
        url: `http://localhost:8080/api/account-book/incomeclassification/66`,
      })
        .then((res) => {
          console.log("deletIncomeClassification")
          console.log(res.data)
        })

    },
    addIncomeclassification() {
      axios({
        method: 'post',
        url: `http://localhost:8080/api/account-book/incomeclassification`,
        data: {
          "name" : "엄마 용돈",
        }
      })
        .then((res) => {
          console.log("addIncomeclassification")
          console.log(res.data)
        })
      
    },
    SpendingClassificationGet() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spendingclassification`,
        
      })
        .then((res) => {
          console.log("spendingclassification")
          console.log(res.data)
        })
      
    },
    SpendingClassificationadd() {
      axios({
        method: 'post',
        url: `http://localhost:8080/api/account-book/spendingclassification`,
        data: {
          "majorSpendingClassificationId" : 0,
          "name" : "승현이랑 외식"
        }
      })
        .then((res) => {
          console.log("spendingclassification")
          console.log(res.data)
        })
      
    },
    deletSpendingClassification() {
      axios({
        method: 'delete',
        url: `http://localhost:8080/api/account-book/spendingclassification/65`
      })
        .then((res) => {
          console.log("delete")
          console.log(res.data)
        })
    },

    updateSpendingClassification() {
      axios({
        method: 'patch',
        url: `http://localhost:8080/api/account-book/spendingclassification/65`,
        data: {
          "majorSpendingClassificationId" : 0,
          "name" : "승현쓰"
        }
      })
        .then((res) => {
          console.log("updateSpendingClassification")
          console.log(res.data)
        })
      
    },


    incomeClassificationGet() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/incomeclassification`,
        
      })
        .then((res) => {
          console.log("incomeClassificationGet")
          console.log(res.data)
        })
      
    },
    AssetGet() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/asset`,
        
      })
        .then((res) => {
          console.log("AssetGet")
          console.log(res.data)
        })
      
    },

    budgetadd() {
      axios({
        method: 'post',
        url: `http://localhost:8080/api/account-book/budget`,
        data: {
          "year" : 2023,
          "month" : 5,
          "amount" : 1000000,
        },
      })
        .then((res) => {
          console.log("add")
          console.log(res.data)
        })
    },
    budgetmonthget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/budget/2023/5`,
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    budgetpatch() {
      axios({
        method: 'patch',
        url: `http://localhost:8080/api/account-book/budget`,
        data: {
          "year" : 2023,
          "month" : 5,
          "amount" : 500000,
        },
      })
        .then((res) => {
          console.log("budgetpatch")
          console.log(res.data)
        })
    },
    budgetdelet() {
      axios({
        method: 'delete',
        url: `http://localhost:8080/api/account-book/budget/2023/5`
      })
        .then((res) => {
          console.log("delete")
          console.log(res.data)
        })
    },


    getMonthTotalAmmount() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/total/amount/2023/5`
      })
      .then((res) => {
        console.log("get")
        console.log(res.data)
      })
    },
    getDateTotalAmmount() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/total/amount/2023/5/4`
      })
      .then((res) => {
        console.log("get")
        console.log(res.data)
      })
    },

    logout() {
      this.$store.state.loginCheck = false
    },
    check() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending?page=0&size=10`
      })
      .then((res) => {
        console.log("get")
        console.log(res.data)
      })
    },
    add() {
      axios({
        method: 'post',
        url: `http://localhost:8080/api/account-book/spending`,
        data: {
          "assetId" : 50,
          "spendingClassificationId" : 32,
          "date" : "2023-05-04",
          "amount" : 50000,
          "detail": "엽닭",
          "memo": "승현이랑"
        },
      })
        .then((res) => {
          console.log("add")
          console.log(res.data)
        })
    },
    get() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending?page=0&size=10`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    monthget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending/2023/5?page=0&size=10`
      })
      .then((res) => {
        console.log("get")
        console.log(res.data)
      })
    },
    detailget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending/47`
      })
      .then((res) => {
        console.log("get")
        console.log(res.data)
      })
    },
    amountget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/spending/amount/2023/5`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    patch() {
      axios({
        method: 'patch',
        url: `http://localhost:8080/api/account-book/spending/47`,
        data: {
          "assetId" : 20,
          "spendingClassificationId" : 2,
          "date" : "2023-05-04",
          "amount" : 54000, 
          "detail": "엽닭",
          "memo": "민지랑승현이랑"
        },
      })
      .then((res) => {
          console.log("patch")
          console.log(res.data)
        })
    },
    delet() {
      axios({
        method: 'delete',
        url: `http://localhost:8080/api/account-book/spending/48`
      })
        .then((res) => {
          console.log("delete")
          console.log(res.data)
        })
    },
    incomeadd() {
      axios({
        method: 'post',
        url: `http://localhost:8080/api/account-book/income`,
        data: {
          "assetId" : 21,
          "incomeClassificationId" : 24,
          "date" : "2023-05-04",
          "amount" : 50000, 
          "detail": "용돈",
          "memo": "승현이가 줌"
        },
      })
        .then((res) => {
          console.log("add")
          console.log(res.data)
        })
    },
    incomegetall() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/income?page=0&size=10`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    incomemonthget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/income/2023/5?page=0&size=10`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    incomedetailget() {
      axios({
        method: 'get',
        url: `http://localhost:8080/api/account-book/income/29`
      })
        .then((res) => {
          console.log("get")
          console.log(res.data)
        })
    },
    incomepatch() {
      axios({
        method: 'patch',
        url: `http://localhost:8080/api/account-book/income/59`,
        data: {
          "assetId" : 21,
          "incomeClassificationId" : 24,
          "date" : "2023-05-09",
          "amount" : 5050,
          "detail": "용돈",
          "memo": "승현이가 줬당"
        },
      })
      .then((res) => {
          console.log("patch")
          console.log(res.data)
        })
    },
    incomedelet() {
      axios({
        method: 'delete',
        url: `http://localhost:8080/api/account-book/income/59`
      })
        .then((res) => {
          console.log("delete")
          console.log(res.data)
        })
    },
  },

  
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}
</style>
