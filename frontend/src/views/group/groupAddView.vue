<template>
    <div>
        <div>
            <h2>그룹 생성</h2>
        </div>

        <div style="display:flex;">
            <div style="font-size:20px; width: 110px; display: flex; margin-top: 3px;">
                <label for="groupName">그룹 이름</label>
            </div>
            <div>
                <input type="text" id="groupName" v-model="groupName" style="border-radius:5px; width: 150px;  height:20px; margin: 5px;">
            </div>
            <button @click="existsGroupName"
            style="background-color:white; border-radius:5px; height:30px; margin-left:5px; margin-top:2px; padding:4px;">중복 체크</button>
        </div>

        <div style="display:flex;">
            <div style="font-size:20px; width: 110px; display: flex; margin-top: 3px;">
                <label for="email">팀원 추가</label>
            </div>
            <div>
                <input type="text" id="email" v-model="email" style="border-radius:5px; width: 150px; height:20px; margin: 5px;">
            </div>
        </div>
        <ul>
                <li v-for="email in emails" :key="email.id" @click="addMemberId(email.id)">
                    {{ email.email }}
                    {{ email.nickname }}
                </li>
            </ul>

            <br><br>
        <div>
            <button @click="addGroup" :disabled="isGroupCreatable"
            style="border:none; background-color:#5987DF; color:white; padding:10px 15px; font-size:15px; font-weight:bold; border-radius:8px;">그룹 생성</button>
        </div>
    </div>
  </template>
  
  <script>
  import { debounce } from 'lodash';

  export default {
    data() {
      return {
        groupName: '',
        email: '',
        emails : [],
        memberIds : [],
        existsCircleName : true,
        isChecked : false,
      }
    },
    computed: {
        isGroupCreatable() {
            return this.groupName == '' || this.existsCircleName;
        },
    },
    methods : {
        existsGroupName () {
            this.axios.get(process.env.VUE_APP_API_URL + `/circle/exists?name=` + this.groupName)
            .then((res) => {
                console.log(res.data);
                this.existsCircleName = res.data;
            })
        },
        searchEmail () {
            this.axios.get(process.env.VUE_APP_API_URL + `/member/search?email=` + this.email)
            .then((res) => {
                this.emails = res.data;
                console.log(res.data);
            })
        },
        addMemberId(id) {
            if (!this.memberIds.includes(id)) {
                this.memberIds.push(id);
            }
        },
        addGroup () {
            this.axios({
                method: 'post',
                url: process.env.VUE_APP_API_URL + `/circle`,
                data: {
                    "name" : this.groupName,
                    "member_ids" : this.memberIds
                },
            })
            .then((res) => {
                console.log(res.data)
                this.$router.push('/group')
            })
            .catch(err => { console.log(err) })
        }
    },
    watch: {
        email: debounce(function() {
            this.searchEmail();
        }, 500) // 500ms 동안 새로운 요청이 없으면 searchEmail 함수를 실행합니다.
        ,
        groupName () {
            this.existsCircleName = true;
        }
    },
    
  
  }
  </script>
  
  <style></style>