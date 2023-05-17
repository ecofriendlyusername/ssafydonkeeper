<template>
    <div>
        <label for="이름">이름</label> <input type="text" name="이름" id="" :value="name" @change="dataChange('name')">
        <label for="시작일">시작일</label> <input type="date" name="시작일" id="" :value="startDate" @change="dataChange('startDate')">
        <label for="완료일">완료일</label> <input type="date" name="완료일" id="" :value="endDate" @change="dataChange('endDate')">
        <label for="콘텐츠">콘텐츠</label> <input type="text" name="콘텐츠" id="" :value="content" @change="dataChange('content')">
    </div>

    <button v-on:click="postChallenge">저장하기</button>
</template>

<script>
export default {
    data(){
        return {
            name: null,
            startDate: null,
            endDate: null,
            content: null,
        }
    },
    methods: {
        dataChange(data){
            this[data] = event.target.value
        },
        postChallenge() {
            this.axios({
                method: 'post',
                url: `http://localhost:8080/api/challenge`,
                data: {
                    "name": this.name,
                    "startDate": this.startDate,
                    "endDate": this.endDate,
                    "content": this.content,
                }
            })
                .then((res) => {
                    console.log("postChallenge")
                    console.log(res.data)
                    this.$router.push('/challange')
                })
        }

        // {
        //     "name" : String,
        //     "startDate" : String Date형식(2023-05-10),
        //     "endDate" : String Date형식(2023-05-10),
        //     "content": String,
        // }

    }

}
</script>

<style></style>