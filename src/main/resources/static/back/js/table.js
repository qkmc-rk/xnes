layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    table.render({
        elem: '#info'
        ,height: 500
        ,url: 'get/certify/all'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,where:{token: window.localStorage.getItem('token'), userid: window.localStorage.getItem('userid')}
        ,page: true //开启分页
        ,cols: [[ //表头
            {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
            ,{field: 'userid', title: 'userid', width:80}
            ,{field: 'state', title: 'state', width:80, sort: true}
            ,{field: 'cardpath', title: 'cardpath', width:80}
            ,{fixed: 'right', width:150, align:'center', toolbar: '#barCRUD'} //这里的toolbar值是模板元素的选择器
        ]]
    });

    table.on('tool(certify)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'detail'){ //查看
            //do somehing
            //alert("查看cardpath:" + data.cardpath);
            //alert("查看userid:" + data.userid);
            //alert("查看id:" + data.id);
            //查看操作首先获取图片名字
            var pic = data.cardpath;
            var root = $('#rootPath').val();
            window.open(root + '/' + pic);


        } else if(layEvent === 'pass'){ //删除
            layer.confirm('真的审核通过么', function(index){
                //向服务端发送删除指令
                $.ajax({
                    type:"get",
                    url:"passcertify/" + data.userid,
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
                                layer.msg('审核通过!');
                            });
                            //更新值
                            obj.update({
                                state: '1'
                            });
                        }else{
                            layui.use('layer',function(){
                                var layer = layui.layer;
                                layer.msg('审核失败!');
                            });
                        }
                    },
                    error:function(){
                        layui.use('layer',function(){
                            var layer = layui.layer;
                            layer.msg('审核失败!');
                        });
                    }
                });
            });
        }
    });

});