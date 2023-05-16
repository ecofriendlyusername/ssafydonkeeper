<template>
    <div>
        <h1>그룹 수정</h1>
        <div style="">
            <div id=""><label for="groupName">그룹 이름</label></div>
            <input type="text" id="groupName" v-model="groupName" style="border-radius:5px; width: 165px;">
            <button @click="existsGroupName">중복 체크</button>
        </div>

        <div >
            <button @click="addGroup" :disabled="isGroupCreatable">이름 변경</button>
        </div>

        <div style="">
            <div id=""><label for="email"></label></div>
            <input type="text" id="email" v-model="email" style="border-radius:5px; width: 165px;">
            <ul>
                <li v-for="email in emails" :key="email.id" @click="addMemberId(email.id)">
                    {{ email.email }}
                    {{ email.nickname }}
                </li>
            </ul>
        </div>

        <div >
            <button @click="inviteMember" :disabled="memberIds.length <= 0">초대</button>
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
        inviteMember () {

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