package ${basePackage}.module.params;

import ${fullEntityClassName};

public class ${entityClassName}Param extends ${entityClassName} {
    private Integer page=1; //当前页
    private Integer pageSize=20;//每页行数

    public ${entityClassName}Param() {
        page = 1;
        pageSize = 20;
    }

}
