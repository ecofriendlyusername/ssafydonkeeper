
<template>
    <div>
        <div id="challangeTitle">
            <div>
                내가 참여중인 챌린지
            </div>
        </div>


        <div v-for="(data, idx) in dataList2" :key="idx" @click="this.$router.push(`/challange/${data.id}`)"
            style="display:flex; justify-content:center;">
            <div class="challangeName">
                <div>
                    {{ data.name }}
                </div>
                <div>
                    >
                </div>
            </div>
        </div>

        <div id="challangeTitle">
            <div>
                참여 가능한 챌린지
            </div>
            <div style="color:#5987DF" @click="this.$router.push('/challange/add')">
                + 추가
            </div>
        </div>


        <div v-for="(data, idx) in dataList" :key="idx" @click="this.$router.push(`/challange/${data.id}`)"
            style="display:flex; justify-content:center;">
            <div class="challangeName">
                <div>
                    {{ data.name }}
                </div>
                <div>
                    >
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            dataList: [],
            dataList2:[]
        }
    },
    methods: {
        getChallengeList() {
            //시작전 챌린지 리스트 잘됨.
            this.axios({
                method: 'get',
                url: `http://localhost:8080/api/challenge/list`,
            })
                .then((res) => {
                    console.log("getChallengeList")
                    console.log(res.data)
                    this.dataList = res.data
                })
        },
        getChallengeList2() {
            //시작전 챌린지 리스트 잘됨.
            this.axios({
                method: 'get',
                url: `http://localhost:8080/api/challenge/progress`,
            })
                .then((res) => {
                    console.log("getChallengeList")
                    console.log(res.data)
                    this.dataList2 = res.data
                })
        },
    },
    mounted() {
        this.getChallengeList()
        this.getChallengeList2()
    }

}
</script>

<style>
#challangeTitle {
    border: 2px solid black;
    width: 80%;
    display: flex;
    justify-content: space-between;
    border-radius: 8px;
    padding: 5px 20px;
    font-weight: bold;
    margin: 15px;
}

.challangeName {
    background-color: #F4F5F7;
    width: 80%;
    display: flex;
    justify-content: space-between;
    border-radius: 8px;
    padding: 10px;
    font-weight: bold;
    margin-top: 10px;
}
</style>