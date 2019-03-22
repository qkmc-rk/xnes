layui.use('table', function(){
    var table = layui.table;

    //��һ��ʵ��
    table.render({
        elem: '#info'
        ,height: 500
        ,url: 'get/certify/all'
        ,cellMinWidth: 80 //ȫ�ֶ��峣�浥Ԫ�����С��ȣ�layui 2.2.1 ����
        ,where:{token: window.localStorage.getItem('token'), userid: window.localStorage.getItem('userid')}
        ,page: true //������ҳ
        ,cols: [[ //��ͷ
            {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
            ,{field: 'userid', title: 'userid', width:80}
            ,{field: 'state', title: 'state', width:80, sort: true}
            ,{field: 'cardpath', title: 'cardpath', width:80}
            ,{fixed: 'right', width:150, align:'center', toolbar: '#barCRUD'} //�����toolbarֵ��ģ��Ԫ�ص�ѡ����
        ]]
    });

    table.on('tool(certify)', function(obj){ //ע��tool�ǹ������¼�����test��tableԭʼ���������� lay-filter="��Ӧ��ֵ"
        var data = obj.data; //��õ�ǰ������
        var layEvent = obj.event; //��� lay-event ��Ӧ��ֵ��Ҳ�����Ǳ�ͷ�� event ������Ӧ��ֵ��
        var tr = obj.tr; //��õ�ǰ�� tr ��DOM����

        if(layEvent === 'detail'){ //�鿴
            //do somehing
            alert("�鿴cardpath:" + data.cardpath);
            alert("�鿴userid:" + data.userid);
            alert("�鿴id:" + data.id);
            //�鿴�������Ȼ�ȡͼƬ����
            var pic = data.cardpath;
            window.open('<%= request.getContextPath()%>/idcardimg/' + pic);


        } else if(layEvent === 'pass'){ //ɾ��
            layer.confirm('������ͨ��ô', function(index){
                //�����˷���ɾ��ָ��
                $.ajax({
                    type:"get",
                    url:"passcertify/" + data.userid,
                    async:true,
                    data:{
                        'userid':window.localStorage.getItem('userid'),
                        'token':window.localStorage.getItem('token')
                    },
                    //���ݷ���ֵ
                    success:function(data2){
                        var obj2 = JSON.parse(data2);
                        if(obj2 != null && obj2.result == 'true'){
                            layui.use('layer',function(){
                                var layer = layui.layer;
                                layer.msg('���ͨ��!');
                            });
                            //����ֵ
                            obj.update({
                                state: '1'
                            });
                        }else{
                            layui.use('layer',function(){
                                var layer = layui.layer;
                                layer.msg('���ʧ��!');
                            });
                        }
                    },
                    error:function(){
                        layui.use('layer',function(){
                            var layer = layui.layer;
                            layer.msg('���ʧ��!');
                        });
                    }
                });
            });
        }
    });

});