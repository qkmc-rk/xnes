layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    table.render({
        elem: '#info'
        ,height: 500
        ,url: 'allinfo'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,where:{token: window.localStorage.getItem('token'), userid: window.localStorage.getItem('userid')}
        ,page: true //开启分页
        ,cols: [[ //表头
            {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
            ,{field: 'userid', title: 'userid', width:80}
            ,{field: 'title', title: '标题', width:80, sort: true}
            ,{field: 'tip', title: '备注', width:80}
            ,{field: 'reward', title: '赏金', width: 177}
            ,{fixed: 'right', width:150, align:'center', toolbar: '#barCRUD'} //这里的toolbar值是模板元素的选择器
        ]]
    });

    table.on('tool(info)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'detail'){ //查看
            //alert("查看" + data.id);
            //跳转页面
            window.open('<%= request.getContextPath()%>' + '/user/work_detail?infoid=' + data.id);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确认删除?', function(index){
                //发送删除指令
                //向服务器发送删除指令
                $.ajax({
                    type:"get",
                    url:"delete/helpinfo/" + data.id,
                    async:true,
                    data:{
                        'userid':window.localStorage.getItem('userid'),
                        'token':window.localStorage.getItem('token')
                    },
                    //根据返回值
                    success:function(data2){
                        var obj2 = JSON.parse(data2);
                        if(obj2 != null && obj2.result == 'true'){
                            layui.use('layer',function(){
                                var layer = layui.layer;
                                layer.msg('删除成功!');
                            });
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);
                        }else{
                            layui.use('layer',function(){
                                var layer = layui.layer;
                                layer.msg('删除失败!');
                            });
                            layer.close(index);
                        }
                    },
                    error:function(){
                        layui.use('layer',function(){
                            var layer = layui.layer;
                            layer.msg('删除失败!');
                        });
                    }
                });
            });
        }
    });
});