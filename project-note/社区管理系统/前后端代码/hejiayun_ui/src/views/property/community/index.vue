<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="小区名称" prop="communityName">
        <el-input
          v-model="queryParams.communityName"
          placeholder="请输入小区名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="小区编码" prop="communityCode">
        <el-input
          v-model="queryParams.communityCode"
          placeholder="请输入小区编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:community:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:community:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:community:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
        <!-- v-hasPermi="['system:community:export']" -->
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="communityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
        <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center">
        </el-table-column>
        <el-table-column v-if="show" label="ID" align="center" prop="communityId" />
      <el-table-column label="小区名称" align="center" prop="communityName" />
      <el-table-column label="小区编码" align="center" prop="communityCode" />
      <el-table-column label="省" align="center" prop="communityProvinceName" />
      <el-table-column label="市" align="center" prop="communityCityName" />
      <el-table-column label="区/县" align="center" prop="communityTownName" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:community:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:community:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-setting"
            @click="replaceProperty(scope.row)"
            v-hasPermi="['system:community:remove']"
          >更换物业</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

     <!-- 更换物业 -->
    <el-dialog :title="title" :visible.sync="property" width="700px" append-to-body>
    <el-table
          v-loading="loading"
          :data="deptList"
          row-key="deptId"
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        >
          <el-table-column prop="deptName" label="物业名称" width="260"></el-table-column>
          <el-table-column prop="status" label="状态" :formatter="statusFormat" width="100"></el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="200">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      type="text"
                      v-if="scope.row.parentId === 100 && scope.row.deptId !== selectedDeptId"
                      icon="el-icon-success"
                      @click="replacePropertyAction(scope.row)"
                      v-hasPermi="['system:dept:add']"
                    >选择</el-button>
                    <el-button
                      size="mini"
                      type="text"
                      v-else-if="scope.row.deptId === selectedDeptId"
                      icon="el-icon-success"
                      >已选择</el-button>
                    </template>
                </el-table-column>
        </el-table>
    </el-dialog>
    <!-- 添加或修改 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="小区名称" prop="communityName">
          <el-input v-model="form.communityName" placeholder="请输入小区名称" />
        </el-form-item>
        <el-form-item label="详细地址" prop="communityDetailedAddress">
          <el-input v-model="form.communityDetailedAddress" placeholder="请输入详细地址" />
        </el-form-item>
         <el-form-item label="所属区划" prop="selected">
                 <el-cascader
                   v-model="selectedAreaInfo"
                   :props="cascaderProps"
                   :options="areaOptions"
                   @change="selectedAreas"
                 ></el-cascader>
              </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCommunity,
         getCommunity,
         delCommunity,
         addCommunity,
         updateCommunity,
         exportCommunity,getAreaTree,getCommunityTree } from "@/api/property/community";
import { listDept, getDept, delDept, addDept, updateDept, listDeptExcludeChild } from "@/api/system/dept";
export default {
  inject: ['reload'],
  name: "Community",
  components: {
  },
  data() {
    return {
      show: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 表格树数据
       deptList: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 小区信息表格数据
      communityList: [],
      //选中的区划信息
      selectedAreaInfo: [],
      //区划信息
       areaOptions: [],
      //级联组件配置
       cascaderProps: {
           value: 'code',
           label: 'name'
       },
      // 弹出层标题
      title: "",
      //小区ID
      publicCommunityId:"",
      //已选择
      selectedDeptId:"",
      // 是否显示弹出层
      open: false,
      // 是否显示更换物业弹出层
      property: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityName: null,
        communityCode: null,
        communityProvinceCode: null,
        communityCityCode: null,
        communityTownCode: null,
        longitude: null,
        latitude: null,
        deptId: null,
        sort: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      communityName: [
             { required: true, message: '小区名称不能为空', trigger: 'blur' }
            ],
      communityDetailedAddress: [
             { required: true, message: '详细地址不能为空', trigger: 'blur' }
            ],
      selected: [
             { required: true, message: '请选择区划', trigger:  'blur' }
            ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
          this.statusOptions = response.data;
        });
  },
  methods: {
      // 字典状态字典翻译
          statusFormat(row, column) {
            return this.selectDictLabel(this.statusOptions, row.status);
          },
     //获取选中的区划信息
       selectedAreas(detail) {
          this.form.selected = detail
          this.selectedAreaInfo = detail
         },
    /** 查询小区信息列表 */
    getList() {
      this.loading = true;
      listCommunity(this.queryParams).then(response => {
        this.communityList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        communityId: null,
        communityName: null,
        communityCode: null,
        communityProvinceCode: null,
        communityCityCode: null,
        communityTownCode: null,
        longitude: null,
        latitude: null,
        deptId: null,
        sort: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        selected: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.communityId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.selectedAreaInfo = [],
      getAreaTree().then(response => {
          this.areaOptions = response.data
      })
      this.open = true;
      this.title = "添加小区信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const communityId = row.communityId || this.ids
      getCommunity(communityId).then(response => {
        this.form = response.data;
        this.selectedAreaInfo[0] = parseInt(this.form.communityProvinceCode)
        this.selectedAreaInfo[1] = parseInt(this.form.communityCityCode)
        this.selectedAreaInfo[2] = parseInt(this.form.communityTownCode)
        this.form.selected = this.selectedAreaInfo;
        getAreaTree().then(response => {
            this.areaOptions = response.data
         })
        this.open = true;
        this.title = "修改小区信息";
      });
    },
    /** 更换物业按钮操作 */
    replaceProperty(row) {
        this.loading = true;
        this.selectedDeptId = row.deptId;
        this.publicCommunityId = row.communityId;
        listDept().then(response => { 
            this.deptList = this.handleTreeAll(response.data, "deptId");
            console.log(JSON.stringify(this.deptList))
            this.loading = false;
          });
        this.property = true;
        this.title = "更换物业";
     },
     //更换物业选择方法
     replacePropertyAction(row) {
        this.form.communityId = this.publicCommunityId;
        this.form.deptId = row.deptId;
        this.$confirm('是否确认更换成该物业?', "警告", {
             confirmButtonText: "确定",
             cancelButtonText: "取消",
             type: "warning",
        }).then(() => {
             updateCommunity(this.form).then(response => {
             this.msgSuccess("物业更换成功");
             this.property = false;
              this.getList();
            })
        }
       )
     },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.communityId != null) {
              this.form.communityProvinceCode = this.selectedAreaInfo[0]
              this.form.communityCityCode = this.selectedAreaInfo[1]
              this.form.communityTownCode = this.selectedAreaInfo[2]
            updateCommunity(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.reload();
            });
          } else {
            this.form.communityProvinceCode = this.selectedAreaInfo[0]
            this.form.communityCityCode = this.selectedAreaInfo[1]
            this.form.communityTownCode = this.selectedAreaInfo[2]
            addCommunity(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.reload();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const communityIds = row.communityId || this.ids;
      this.$confirm('是否确认删除小区信息编号为"' + communityIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCommunity(communityIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有小区信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          window.location = 'http://localhost:8080/exportExcel/exportCommunityExcel?pageNum=1&pageSize=10'
        }).catch(error => {
          console.error('导出Excel失败', error);
          // 处理导出失败的情况，如弹出错误提示等
        });
      // this.$confirm('是否确认导出所有小区信息数据项?', "警告", {
      //     confirmButtonText: "确定",
      //     cancelButtonText: "取消",
      //     type: "warning"
      //   }).then(function() {
      //     return exportCommunity(queryParams);
      //   }).then(response => {
      //     this.download(response.msg);
      //   })
    }

  }
};
</script>
