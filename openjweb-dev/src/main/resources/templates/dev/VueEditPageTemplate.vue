<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
  
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
 
       
     <%
          for(field in fieldList){
          %>
          ${field.vueEditListItem}
          <%}%>
    
 
       
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { save ,queryDetail} from '@/api/${classNameLower}'
  import { getDictionaryData } from '@/api/dictionary'
  import DictionaryComponent from '@/components/DictionaryComponent'
 
   
  export default {
     components: { DictionaryComponent },
    data() {
      return {
         
         
        form: {
          <%
          for(field in fieldList){
          %>
          ${field.fieldName}:${field.defaultValue}
          <%}%>




          
         

        },
        rules: {
          //以后再补充检验规则
        },
        title: '',
        dialogFormVisible: false
      }
    },
    created() {
       
       
    },
    methods: {
       changeCode(key, value) {
        this.ruleForm = {
          ...this.ruleForm,
          [key]: value
        }
      },
       
    
      async showEdit(row) {
        if (!row) {
          this.title = '添加${funName}'
        } else {
          this.title = '编辑${funName}'
          const { data } = await queryDetail({
            rowId: row.rowId
          })
          //console.log(data)
          this.form = Object.assign({}, data)
          

        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form // 初始化的值
        this.dialogFormVisible = false
      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const { msg } = await save(this.form)
            this.$baseMessage(msg, 'success')
            this.$refs['form'].resetFields()
            this.dialogFormVisible = false
            this.$emit('fetch-data')
            this.form = this.$options.data().form // 初始化的值
          } else {
            return false
          }
        })
      }
    }
  }
</script>
<style lang="scss" scoped>
  .radio-group-items {
    ::v-deep .radio-item {
      margin: 5px 0;
    }
  }
</style>
