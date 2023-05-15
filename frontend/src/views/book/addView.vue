<template>
  <div class="DivH">
    <hr style="height:3px; background-color:black;">
    <div style="display:flex; margin-top: -20px; justify-content: center; margin-left: 20px; align-items: center;">
      <div>
        <h2 v-on:click="flag = !flag" v-if="flag">수입</h2>
        <h2 v-on:click="flag = !flag" v-else>지출</h2>
      </div>
      <div v-on:click="flag = !flag" style="margin-top:5px;">
        <img src="@/assets/바꾸기.png" style="width:23px; margin: 5px;">
      </div>
    </div>

    <hr style="height:1px; background-color:gray; margin-top: -10px;">
    

    <label for="assetId" style="font-weight:bold; font-size:20px;">
      소득분류
    </label>
    
      <div type="text" id="assetId" :value="AC.name"
        v-on:click="aModalFlag = !aModalFlag">
        {{ AC.name }}
      </div>

    <div class="modal" v-show="aModalFlag">
      <div>
        <div v-for="el in AC_data" :key="el.id" v-on:click="AC = el" id="selecadd" style="display:inline-block">
            <div>{{ el.name }}</div>
        </div>
      </div>
    </div>

    <br><br>
    <label for="classificationId" style="font-weight:bold; font-size:20px;">
      지출분류
    </label>

      <div type="text" id="classificationId" :value="flag ? IC.name : SC.name" v-on:click="cModalFlag = !cModalFlag">
        {{ flag ? IC.name : SC.name }}
      </div>


    <div class="modal" v-show="cModalFlag && !flag">
      <div>
        <li v-for="el in SC_data" :key="el.id" v-on:click="SC = el" id="selecadd" style="display:inline-block">
          {{ el.name }}
        </li>
      </div>
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

    <div style="display:flex; padding: 5px;">
      <div id="addsub"><label for="date">날짜</label></div>
      <input type="date" id="date" style="border-radius:5px; width: 165px;">
    </div>

    <div style="display:flex; padding: 5px;">
      <div id="addsub"><label for="amount">금액</label></div>
      <input type="number" id="amount" style="border-radius:5px; width: 165px;">
    </div>

    <div style="display:flex; padding: 5px;">
      <div id="addsub"><label for="detail">장소</label></div>
      <input type="text" id="detail" style="border-radius:5px; width: 165px;">
    </div>

    <div style="display:flex; padding: 5px;">
      <div id="addsub"><label for="memo">메모</label></div>
      <input type="text" id="memo" style="border-radius:5px; width: 165px;">
    </div>

    <br> <br>

    <button v-on:click=postData() id="saveBtn">저장하기</button>
  </div>
</template>

<script>

export default {
  data() {
    return {
      flag: false,
      aModalFlag: false,
      cModalFlag: false,
      IC: { name: '선택' },
      IC_data: [],
      SC: { name: '선택' },
      SC_data: [],
      AC: { name: '선택' },
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

#selecadd {
  width: 100px;
  background-color:#f7f7f7;
  padding:5px;
  margin: 5px;
  border-radius: 5px;
}

#assetId {
  display: inline-block;
  background-color: #4D82E6;
  color: white;
  width: 50px;
  margin: 5px;
  font-weight: bold;
  padding: 5px;
  border-radius: 10px;
}

#classificationId {
  display: inline-block;
  background-color: #4D82E6;
  color: white;
  width: 50px;
  margin: 5px;
  font-weight: bold;
  padding: 5px;
  border-radius: 10px;
}

#addsub {
  width: 180px;
  font-size: 20px;
  font-weight: bold;
}

#saveBtn {
  background-color: #5E29F6;
  color: white;
  padding: 10px;
  border-radius: 10px;
  font-weight: bold;
  border: none;
}


</style>