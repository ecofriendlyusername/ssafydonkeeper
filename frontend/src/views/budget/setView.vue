<template>
  <div>
    <h1>예산 설정 페이지</h1>

    <div class="budget">
      <label for="total_budget">전체 예산: </label><input type="number" name="total_budget" :value="total_budget" @change="logging_total()">
    </div>

    <div>
      <h3>카테고리별 예산</h3>
      <h3>
        {{ total_budget - sum_budget }}원 남음
      </h3>
    </div>

    <div v-for="(budget, idx) in budget_list" :key="idx">
      <label :for="budget.name">{{ budget.name }}</label>
      <input type="number" :name="budget.name" :value="budget.amount" @change="logging(idx)">
      <button @click="delList(budget.id)">X</button>
    </div>

    <div v-if="!flag" @click="flag = true">카테고리 열기</div>
    <div v-else @click="flag = false">카테고리 닫기</div>
    <div v-if="flag">
      <p v-for="(classification, idx) in classifications" :key="idx" @click="addList(classification)" class="budget">
        {{ classification.name }}
      </p>
    </div>

    <button v-on:click="checkData()">예산 저장하기</button>

  </div>
</template>

<script>
export default {
  data() {
    return {
      flag: false,
      classifications: [],
      budget_list: [],
      total_budget: 0,
      sum_budget: 0,
      today: new Date()
    }
  },
  methods: {
    logging(index) {
      this.budget_list[index].amount = event.target.value;
      this.sum_budget = this.budget_list.reduce((o, t) => o + parseInt(t.amount), 0)
    },
    logging_total() {
      this.total_budget = event.target.value;
    },
    addList(data) {
      let flag = true;
      this.budget_list.filter(el => {
        if (el.id == data.id) {
          flag = false;
        }
      });
      if (flag) {
        data.amount = 0;
        this.budget_list.push(data);
      }
    },

    delList(key) {
      this.budget_list = this.budget_list.filter(el => {
        if (el.id != key) {
          return el
        }
      })
    },

    checkBudget(data) {
      console.log(data);
    },

    getData() {
      this.axios.get(process.env.VUE_APP_API_URL + `/account-book/spendingclassification`)
        .then(res => {
          console.log(res.data);
          this.classifications = res.data.map(el => {
            return { "id": el.id, "name": el.name, "amount": 0 }
          })
        })
    },
    checkData() {
      let flag = false
      this.budget_list.forEach((el) => {
        console.log(el);
        if (el.amount <= 0) {
          flag = true
        } 
      })
      console.log(flag);
      if (flag) {
        this.postData()      
      } else {
        alert("예산을 입력해주세요.")
      }
    },
    postData() {
      this.axios({
        method: 'patch',
        url: process.env.VUE_APP_API_URL + `/account-book/budget`,
        data: {
          "year": this.today.getFullYear(),
          "month": this.today.getMonth()+1,
          "total_amount": this.total_budget,
          "datas": this.budget_list.map(el => {
            return {
              "classificationId": el.id, 
              "amount": el.amount 
            }
          })
        }
      })
      .then(res => {
        console.log(res.data);
        this.budget_list = []
      })
      .then(()=>{
        this.$router.push('/budget')
      })
      .catch((err) => {
        console.log(err);
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
  /* font-size: 24px; */
  padding: 4px;
}
</style>