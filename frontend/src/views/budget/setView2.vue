<template>
    <div>
        <h4 style="display:flex; margin-left: 15px;">한 달 예산</h4>
        <div style="margin-top: -15px; font-weight: bold; font-size: 20px;">
            <label for="total_budget" style="margin-left:-80px;">원</label>
            <input
                type="number"
                name="total_budget"
                :value="total_budget"
                style="display:flex; margin-left: 15px; width: 30%; height: 22px; margin-top: -26px; border: 2px solid black; border-radius: 8px;"
                @input="handleInput"
                @change="total_budget = $event.target.value"
                pattern="[0-9]*"
                inputmode="numeric"
            >
        </div>

        <div style="display:flex; margin: 10px 0px; background-color: #F4F4F4; justify-content: space-between; padding: 5px 15px;">
            <div style="font-weight:bold;">
                남은 예산
            </div>
            <div style="color:gray;">
                전체 {{ this.total_budget.toLocaleString() }}원 중 {{ (this.total_budget - this.totalClassificationBudget).toLocaleString() }}원 남음
            </div>
        </div>

        <div style="margin-left:-45px">
            <div v-for="(classification, idx) in classifications" :key="idx" @click="addList(classification)" class="budget">
                {{ classification.name }}
            </div>
        </div>

        <div v-for="(item, idx) in spendingClassificationBudget" :key="idx" style="display:flex; margin:15px 15px;">
            <div style="width:80px; display:flex; font-weight: bold;">
                <label :for="item.name">{{ item.name }}</label>
            </div>
            <input type="number" 
                :name="item.name" 
                :value="item.amount"
                @input="handleInput"
                @change="item.amount = $event.target.value"
                pattern="[0-9]*"
                inputmode="numeric"
            >
            <button @click="delList(item.classificationId)" id="Xbtn">X</button>
        </div>

        <br>
        <br>
        <br>

        <button @click="checkData()"
            style="margin-left: 15px; color:black; width:105px; padding: 10px 10px; font-weight: bold; border: none; border-radius: 10px;">
            예산 저장하기
        </button>

    </div>
</template>

<script>
export default {
    data() {
        return {
            year: new Date().getFullYear(),
            month: new Date().getMonth() + 1,
            total_budget: 0,
            budgetResponse: [],
            spendingClassificationBudget: [],
            hasClassificationBudget: false,
            totalClassificationBudget: 0,
            classifications: [],
        }
    },
    methods: {
        addList(classification) {
            // spendingClassificationBudget에 이미 classification id 가 있으면 그냥 넘어가고,
            const hasValue = this.spendingClassificationBudget.find(item => item.classificationId === classification.id);
            // 없으면 amount 0 으로 classification id 추가.
            if (hasValue === undefined) {
                this.spendingClassificationBudget.push(
                    { "classificationId" : classification.id, "name" : classification.name, "amount" : hasValue ? hasValue.amount : 0 }
                );
            }
        },
        handleInput(event) {
            // 앞의 0 제거
            const inputValue = event.target.value.toString();
            
            if (inputValue.length > 1 && inputValue.charAt(0) === '0') {
                event.target.value = parseInt(inputValue.slice(1), 10);
            } else {
                event.target.value = parseInt(inputValue, 10);
            }
        },
        checkData() {
            // spendingClassificationBudget 돌면서 total_budget 보다 크면 exception,
            let sumVal = 0;
            this.spendingClassificationBudget.forEach(element => {
                sumVal += parseInt(element.amount);
            });

            if (sumVal > this.total_budget) {
                alert("세부 예산이 한 달 전체 예산보다 큽니다. 확인해주세요.");
                return;
            }

            // spendingClassificationBudget에 amount = 0 인 게 있으면 저장 x
            this.spendingClassificationBudget = this.spendingClassificationBudget.filter(item => item.amount > 0);

            // 체크 후 저장
            this.setBudget()
        },
        setBudget() {
            this.axios({
                method: 'patch',
                url: process.env.VUE_APP_API_URL + `/account-book/budget`,
                data: {
                    "year": this.year,
                    "month": this.month,
                    "total_amount": this.total_budget,
                    "datas": this.spendingClassificationBudget.map(el => {
                        return {
                            "classificationId": el.classificationId, 
                            "amount": el.amount 
                        }
                    })
                }
            })
            .then(res => {
                console.log(res.data);
                this.$router.push('/budget')
            })
        },
        delList(key) {
            this.spendingClassificationBudget = this.spendingClassificationBudget.filter(el => {
                if (el.classificationId != key) {
                    return el
                }
            })

        },

    },
    mounted() {
        this.axios.get(process.env.VUE_APP_API_URL + `/account-book/spendingclassification`)
        .then(res => {
            this.classifications = res.data;
        })
        
        this.axios.get(process.env.VUE_APP_API_URL + `/account-book/budget/all/${this.year}/${this.month}`)
        .then(res => {
            this.budgetResponse = res.data;
            this.spendingClassificationBudget = res.data.datas;
            this.total_budget = res.data.total_amount;

            if (res.data.datas.length > 0) {
                this.hasClassificationBudget = true;
            }
            
            let sumAmount = 0;
            res.data.datas.forEach(element => {
                if (element.amount > 0) {
                    sumAmount += element.amount;
                }
            });
            
            this.totalClassificationBudget = sumAmount;
        })
    }
};
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
</style>