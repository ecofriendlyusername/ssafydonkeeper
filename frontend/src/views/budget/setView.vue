<template>
  <div>
    <h4 style="display:flex; margin-left: 15px;">한 달 예산</h4>
    <div style="margin-top: -15px; font-weight: bold; font-size: 20px;">
      <label for="total_budget" style="margin-left:-80px;">원</label>
      <input type="number" name="total_budget" :value="total_budget" @change="logging_total()"
      style="display:flex; margin-left: 15px; width: 30%; height: 22px; margin-top: -26px; border: 2px solid black; border-radius: 8px;">
    </div>


    <div style="display:flex; margin: 10px 0px; background-color: #F4F4F4; justify-content: space-between; padding: 5px 15px;">
      <div style="font-weight:bold;">
        남은 예산
      </div>
      <div style="color:gray;">
        전체 {{ total_budget }}원 중 {{ total_budget - sum_budget }}원 남음
      </div>
    </div>


    <div v-if="!flag" @click="flag = true"
    style="display:flex; justify-content:center; margin-left: 15px; background-color:#4D82E6; width:105px; padding: 5px 10px; color: white; font-weight: bold; border: none; border-radius: 10px; ">
      세부예산 설정
    </div>


    <!-- <div v-if="!flag" @click="flag = true">세부예산 설정</div> -->
    <div v-else @click="flag = false"
    style="display:flex; justify-content:center; margin-left: 15px; background-color:#4D82E6; width:105px; padding: 5px 10px; color: white; font-weight: bold; border: none; border-radius: 10px; "
    >설정 완료</div>

    <div v-if="flag" >
      <div v-for="(budget, idx) in budget_list" :key="idx"
      style="display:flex; margin:15px 15px;">
      <div style="width:80px; display:flex; font-weight: bold;">
        <label :for="budget.name">{{ budget.name }}</label>
      </div>
        <input type="number" :name="budget.name" :value="budget.amount" @change="logging(idx)">
        <button @click="delList(budget.id)" id="Xbtn">X</button>
      </div>
    </div>

    <br>
    <div v-if="flag" style="margin-left:-45px">
      <div v-for="(classification, idx) in classifications" :key="idx" @click="addList(classification)" class="budget">
        {{ classification.name }}
      </div>
    </div>



    <br><br><br>
    <button v-on:click="checkData()"
    style="margin-left: 15px; color:black; width:105px; padding: 10px 10px; font-weight: bold; border: none; border-radius: 10px;">예산 저장하기</button>

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
      today: new Date(),
      data: [],
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
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

        this.axios.get(process.env.VUE_APP_API_URL + `/account-book/budget/all/${this.year}/${this.month}`)
        .then(res => {
          this.data = res.data;
          this.total_budget = res.data.total_amount != -1 ? res.data.total_amount : 0;

          // if (res.data.datas.length > 0) {
          //   this.flag = true
          // }

          let f = true;
          for (var i = 0; i < res.data.datas.length; i++) {
            var da = res.data.datas[i];
            this.budget_list.filter(el => {
              if (el.id == da.id) {
                f = false;
              }
            });
            if (f) {
              // da.amount = 0;
              this.budget_list.push(da);
            }
          }
          console.log(res.data);
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
        alert("예산을 입력해주세요.")
      } else {
        this.postData()
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
  display: inline-block;
  margin:5px 10px;
  border: 2px solid black;
  padding: 5px;
  width: 90px;
  font-size: 12px;
  font-weight: bold;
  border-radius: 5px;
}

#Xbtn {
  border: 1px solid black;
  font-weight: bold;
  background-color: white;
  margin: 0px 10px;
  border-radius: 5px;
}
</style>
