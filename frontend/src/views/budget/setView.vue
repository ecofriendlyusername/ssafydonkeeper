<template>
  <div>
    <h1>예산 설정 페이지</h1>

    <div class="budget">
      <label for="total_budget">전체 예산: </label><input type="number" name="total_budget" :value="total_budget">
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

    <button v-on:click="postData()">예산 저장하기</button>

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
      today: new Date()
    }
  },
  methods: {
    logging(index) {
      this.budget_list[index].amount = event.target.value;
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
          this.classifications = res.data.map(el => {
            return { "id": el.id, "name": el.name, "amount": 0 }
          })
        })
    },

    postData() {
      console.log(this.budget_list)
      this.axios({
        method: 'patch',
        url: process.env.VUE_APP_API_URL + `/account-book/spendingclassification`,
        data: {
          "year": this.today.getFullYear(),
          "month": this.today.getMonth(),
          "total_amount": 5000000 ,
          "datas": this.budget_list.map(el => {
            return {
              "classificationId": el.id, 
              "amount": el.amount 
            }
          })
        }
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