<template>
  <div class="table-container">
    <vab-query-form>
      <vab-query-form-top-panel>
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <%
          for(field in fieldList){
          %>
          ${field.vueQueryListItem}
          <%}%>

          <el-form-item>
            <el-button
                icon="el-icon-search"
                type="primary"
                native-type="submit"
                @click="handleQuery"
            >
              查询
            </el-button>
            <el-button @click="resetData">重置</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-top-panel>
      <vab-query-form-left-panel :span="24">
        <el-button icon="el-icon-plus" type="primary" @click="handleEdit()">
          添加
        </el-button>
        <el-button type="danger" @click="handleDelete()">批量删除</el-button>

      </vab-query-form-left-panel>
    </vab-query-form>
    <el-table
        v-loading="listLoading"
        :data="list"
        :element-loading-text="elementLoadingText"
        row-key="rowId"
        :height="height"
        border
        default-expand-all
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        @selection-change="setSelectRows"
    >
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column
          show-overflow-tooltip
          type="index"
          label="序号"
          align="center"
      ></el-table-column>

     <!-- <el-table-column
          show-overflow-tooltip
          prop="supCode"
          label="短信商编码"
          sortable
      ></el-table-column>
      -->

      <%
      for(field in fieldList){
      %>
      ${field.vueTableListItem}
      <%}%>




      <el-table-column show-overflow-tooltip label="操作" width="200">
        <template #default="{ row }">
          <el-button type="text" @click="handleEdit(row)">编辑</el-button>
          <el-button
              type="text"
              class="red-text-color"
              @click="handleDelete(row)"
          >
            删除
          </el-button>


        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        :background="false"
        :current-page="queryForm.pageNo"
        :layout="layout"
        :page-size="queryForm.pageSize"
        :total="total"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
    ></el-pagination>
    <sms-supplier-edit ref="edit" @fetch-data="fetchData"></sms-supplier-edit>

  </div>
</template>

<script>
import { getList, del} from '@/api/${classNameLower}}'
import ${entityClassName}Edit from './components/${entityClassName}Edit.vue'

import { getDictionaryData } from '@/api/dictionary'
export default {
  name: '${entityClassName}Manager',
  components: {
    ${entityClassName}Edit,
  },
  data() {
    return {
      queryForm: {
        pageNo: 1,
        pageSize: 20
      },
      list: [],
      smsList:[],
      listLoading: true,
      elementLoadingText: '正在加载...',
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      background: false,
      selectRows: []
    }
  },

  computed: {
    height() {
      return this.$baseTableHeight()
    }
  },
  async created() {
    this.getSmsList();//供应商列表
    this.fetchData()
  },
  methods: {
    handleAdd() {
      this.$refs['edit'].showEdit()


    },
    async getSmsList() { //这个名字最好统一
      // 获取公司类型字典
      const { data } = await getDictionaryData('SMS_VENDOR')
      this.smsList = data || []
    },

    handleEdit(row) {
      this.$refs['edit'].showEdit(row)


    },
    setSelectRows(val) {
      this.selectRows = val
    },

    handleDelete(row) {
      if (row&&row.rowId) {
        this.$baseConfirm('你确定要删除该记录吗?', null, async () => {
          const { msg } = await del(row.rowId)
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows.length > 0) {
          const ids = this.selectRows.map((item) => item.rowId).join()
          this.$baseConfirm('你确定要删除选中项吗?', null, async () => {
            const { msg } = await del(ids)
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      }
    },
    async fetchData() {
      this.listLoading = true

      const { data, count } = await getList(this.queryForm)
      this.list = data
      this.total = count
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    handleSizeChange(val) {
      this.queryForm.pageNo = 1
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handleQuery() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    resetData() {
      this.queryForm = {
        pageNo: 1,
        pageSize: 20
      }
      this.fetchData()
    }
  }
}
</script>
<style lang="scss" scoped></style>
