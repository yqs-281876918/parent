/**
 * 模块JavaScript
 */
var contestDetailPage = {
    data:{
        contest: null,
        questions: [],
        scoreList: [],
        questionsAnswer: [],
        currentQuestionIndex: 0,
        antiCount: 0,
    },
    init: function (contest, questions,scoreList) {
        contestDetailPage.data.contest = contest;
        contestDetailPage.data.questions = questions;
        contestDetailPage.data.scoreList = scoreList

        for (var i=0;i<contestDetailPage.data.questions.length;i++){
            if (contestDetailPage.data.questions[i].type == 'completion'){
                var completionAnsList = []
                for (var j=0;j<contestDetailPage.data.questions[i].answers.length;j++){
                    completionAnsList.push('')
                }
                contestDetailPage.data.questionsAnswer.push(
                    {'subjectId':contestDetailPage.data.questions[i].id,
                        'subjectType':contestDetailPage.data.questions[i].type,
                        'score':-1,
                    'answerList':completionAnsList})
            }else {
                var ansList = []
                ansList.push('')
                contestDetailPage.data.questionsAnswer.push(
                    {'subjectId':contestDetailPage.data.questions[i].id,
                        'subjectType':contestDetailPage.data.questions[i].type,
                        'score':-1,
                        'answerList':ansList})
            }
        }
        contestDetailPage.showInit();
    },
    initAgain: function (contest, questions,scoreList,examDetail) {
        contestDetailPage.data.contest = contest;
        contestDetailPage.data.questions = questions;
        contestDetailPage.data.scoreList = scoreList

        contestDetailPage.data.questionsAnswer = examDetail.answers
        contestDetailPage.data.antiCount = examDetail.antiCount
        contestDetailPage.showInit();
    },
    showInit: function (){
        var nowTime = new Date();
        var nowTimeStamp = nowTime.getTime()
        if (nowTimeStamp>(contestDetailPage.data.contest.startTime+contestDetailPage.data.contest.testTime*60000)){
            window.location.href = '/student/exam/list?cno='+contestDetailPage.data.contest.classID
        }

        //TODO::考试时间倒计时
        $("#contestTimeCountdown").countdown(new Date(contestDetailPage.data.contest.startTime+
            contestDetailPage.data.contest.testTime*60000), function (event) {
            // 事件格式
            var format = event.strftime("%D:%H:%M:%S");
            //console.log(format);
            $("#contestTimeCountdown").html(format);
        }).on('finish.countdown', function () {
            // TODO::交卷事件触发
            contestDetailPage.finishContestAction();
        });

        if (contestDetailPage.data.questions[0].type == 'singleChoiceQuestion') {
            $('#currentQuetionTitle').html('(单选)'+'('+contestDetailPage.data.scoreList[0]+'分)'+contestDetailPage.data.questions[0].description);
            var selectOptionStr =
                '  <div class="grouped fields">\n' +
                '    <div class="field">\n';
            for (var i=0;i<contestDetailPage.data.questions[0].optionCount;i++){
                selectOptionStr +=
                    '<div class="ui toggle checkbox" style="display: inline">\n' +
                    '   <input type="radio" name="questionAnswer" value="'+String.fromCharCode(65+i)+'"/>\n' +
                    '   <label>'+String.fromCharCode(65+i)+'&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                    '</div>\n'
            }
            selectOptionStr +=
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);
            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[0].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[0].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.questions[0].type == 'multipleChoiceQuestion') {
            $('#currentQuetionTitle').html('(多选)'+'('+contestDetailPage.data.scoreList[0]+'分)'+contestDetailPage.data.questions[0].description);
            var selectOptionStr =
                '  <div class="grouped fields">\n' +
                '    <div class="field">\n';
            for (var i=0;i<contestDetailPage.data.questions[0].optionCount;i++){
                selectOptionStr +=
                    '<div class="ui toggle checkbox" style="display: inline">\n' +
                    '   <input type="checkbox" name="questionAnswer" value="'+String.fromCharCode(65+i)+'"/>\n' +
                    '   <label>'+String.fromCharCode(65+i)+'&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                    '</div>\n'
            }
            selectOptionStr +=
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[0].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[0].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.questions[0].type == 'completion') {
            $('#currentQuetionTitle').html('(填空)'+'('+contestDetailPage.data.scoreList[0]+'分)'+contestDetailPage.data.questions[0].description);
            var selectOptionStr =
                '  <div class="grouped fields">\n';
            for (var i=0;i<contestDetailPage.data.questions[0].answers.length;i++){
                selectOptionStr +=
                    '<div class="field">\n' + '('+(i+1)+').&nbsp;&nbsp;'+
                    '   <textarea name="questionAnswer" id="'+i+'"/>\n' +
                    '</div>\n'
            }
            selectOptionStr +=
                '    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            for (var i=0;i<contestDetailPage.data.questionsAnswer[0].answerList.length;i++){
                var answerStr = '#'+i;
                $(answerStr).val(contestDetailPage.data.questionsAnswer[0].answerList[i])
            }
        } else if (contestDetailPage.data.questions[0].type == 'judgment') {
            $('#currentQuetionTitle').html('(判断)'+'('+contestDetailPage.data.scoreList[0]+'分)'+contestDetailPage.data.questions[0].description);
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="T"/>\n' +
                '        <label>True&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="F"/>\n' +
                '        <label>False&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[0].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[0].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.questions[0].type == 'combinationChoice') {
            $('#currentQuetionTitle').html('(组合选择)'+'('+contestDetailPage.data.scoreList[0]+'分)'+contestDetailPage.data.questions[0].description);
            var topics = contestDetailPage.data.questions[0].answers;
            var answerLength = topics.length;
            var topic = topics[answerLength-1]
            var topicCount = topic.split("-")[0]
            var selectOptionStr = ''
            for (var j=0;j<topicCount;j++){
                selectOptionStr +=
                    '  <div class="grouped fields">\n' +
                    '    <div class="field">\n'+(j+1)+'.&nbsp;&nbsp;';
                for (var i=0;i<contestDetailPage.data.questions[0].optionCount;i++){
                    selectOptionStr +=
                        '<div class="ui toggle checkbox" style="display: inline">\n' +
                        '   <input type="checkbox" name="questionAnswer" value="'+(j+1)+'-'+String.fromCharCode(65+i)+'"/>\n' +
                        '   <label>'+String.fromCharCode(65+i)+'&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                        '</div>\n'
                }
                selectOptionStr +=
                    '    </div>\n' +
                    '  </div>';
            }
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[0].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[0].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.questions[0].type == 'programProblem') {
            $('#currentQuetionTitle').html('(程序题)'+'('+contestDetailPage.data.scoreList[0]+'分)'+contestDetailPage.data.questions[0].description);
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  name="questionAnswer" id="questionAnswer" rows="20"></textarea>\n' +
                '                    </div>\n' +
                '<button class="ui basic loading button" onclick="contestDetailPage.submitCode()">提交代码</button>\n' +
                '<div class="programProblemCodeSCore">\n' +
                '</div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[0].answerList[0] != '') {
                $('#questionAnswer').val(contestDetailPage.data.questionsAnswer[0].answerList[0]);
            }
        } else {
            $('#currentQuetionTitle').html('(综合题)'+'('+contestDetailPage.data.scoreList[0]+'分)'+contestDetailPage.data.questions[0].description);
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  id="questionAnswer" rows="20"></textarea>\n' +
                '                    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[0].answerList[0] != '') {
                $('#questionAnswer').val(contestDetailPage.data.questionsAnswer[0].answerList[0]);
            }
        }
        //显示按钮
        contestDetailPage.buttonShow()
    },
    targetQuestionAction: function (index) {
        //记录答案
        contestDetailPage.saveCurrentAnswer()

        contestDetailPage.data.currentQuestionIndex = index;

        if (contestDetailPage.data.questions[index].type == 'singleChoiceQuestion') {
            $('#currentQuetionTitle').html('(单选)'+'('+contestDetailPage.data.scoreList[index]+'分)'+contestDetailPage.data.questions[index].description);
            var selectOptionStr =
                '  <div class="grouped fields">\n' +
                '    <div class="field">\n';
            for (var i=0;i<contestDetailPage.data.questions[index].optionCount;i++){
                selectOptionStr +=
                    '<div class="ui toggle checkbox" style="display: inline">\n' +
                    '   <input type="radio" name="questionAnswer" value="'+String.fromCharCode(65+i)+'"/>\n' +
                    '   <label>'+String.fromCharCode(65+i)+'&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                    '</div>\n'
            }
            selectOptionStr +=
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[index].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[index].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.questions[index].type == 'multipleChoiceQuestion') {
            $('#currentQuetionTitle').html('(多选)'+'('+contestDetailPage.data.scoreList[index]+'分)'+contestDetailPage.data.questions[index].description);
            var selectOptionStr =
                '  <div class="grouped fields">\n' +
                '    <div class="field">\n';
            for (var i=0;i<contestDetailPage.data.questions[index].optionCount;i++){
                selectOptionStr +=
                    '<div class="ui toggle checkbox" style="display: inline">\n' +
                    '   <input type="checkbox" name="questionAnswer" value="'+String.fromCharCode(65+i)+'"/>\n' +
                    '   <label>'+String.fromCharCode(65+i)+'&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                    '</div>\n'
            }
            selectOptionStr +=
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[index].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[index].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.questions[index].type == 'completion') {
            $('#currentQuetionTitle').html('(填空)'+'('+contestDetailPage.data.scoreList[index]+'分)'+contestDetailPage.data.questions[index].description);
            var selectOptionStr =
                '  <div class="grouped fields">\n';
            for (var i=0;i<contestDetailPage.data.questions[index].answers.length;i++){
                selectOptionStr +=
                    '<div class="field">\n' + '('+(i+1)+').&nbsp;&nbsp;'+
                    '   <textarea name="questionAnswer" id="'+i+'"/>\n' +
                    '</div>\n'
            }
            selectOptionStr +=
                '    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            for (var i=0;i<contestDetailPage.data.questionsAnswer[index].answerList.length;i++){
                var answerStr = '#'+i;
                $(answerStr).val(contestDetailPage.data.questionsAnswer[index].answerList[i])
            }
        }else if (contestDetailPage.data.questions[index].type == 'judgment') {
            $('#currentQuetionTitle').html('(判断)'+'('+contestDetailPage.data.scoreList[index]+'分)'+contestDetailPage.data.questions[index].description);
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="T"/>\n' +
                '        <label>True&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="F"/>\n' +
                '        <label>False&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[index].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[index].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        }else if (contestDetailPage.data.questions[index].type == 'combinationChoice') {
            $('#currentQuetionTitle').html('(组合选择)'+'('+contestDetailPage.data.scoreList[index]+'分)'+contestDetailPage.data.questions[index].description);
            var topics = contestDetailPage.data.questions[index].answers;
            var answerLength = topics.length;
            var topic = topics[answerLength-1]
            console.log(topic)
            var topicCount = topic.split("-")[0]
            console.log(topicCount)
            var selectOptionStr = ''
            for (var j=0;j<topicCount;j++){
                selectOptionStr +=
                    '  <div class="grouped fields">\n' +
                    '    <div class="field">\n'+(j+1)+'.&nbsp;&nbsp;';
                for (var i=0;i<contestDetailPage.data.questions[index].optionCount;i++){
                    selectOptionStr +=
                        '<div class="ui toggle checkbox" style="display: inline">\n' +
                        '   <input type="checkbox" name="questionAnswer" value="'+(j+1)+'-'+String.fromCharCode(65+i)+'"/>\n' +
                        '   <label>'+String.fromCharCode(65+i)+'&nbsp;&nbsp;&nbsp;&nbsp;</label>\n' +
                        '</div>\n'
                }
                selectOptionStr +=
                    '    </div>\n' +
                    '  </div>';
            }
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[index].answerList[0] != '') {
                $.each($("input[name='questionAnswer']"),function(){
                    if (contestDetailPage.data.questionsAnswer[index].answerList[0].indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        }else if (contestDetailPage.data.questions[index].type == 'programProblem') {
            $('#currentQuetionTitle').html('(程序题)'+'('+contestDetailPage.data.scoreList[index]+'分)'+contestDetailPage.data.questions[index].description);
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  name="questionAnswer" id="questionAnswer" rows="20"></textarea>\n' +
                '                   </div>\n' +
                '<div id="programProblemButton">\n' +
                '<button class="ui positive basic button" onclick="contestDetailPage.submitCode()">提交代码</button>\n' +
                '</div>\n' +
                '<div id="programProblemCodeSCore">\n' +
                '</div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[index].answerList[0] != '') {
                $('#questionAnswer').val(contestDetailPage.data.questionsAnswer[index].answerList[0]);
            }
        }else {
            $('#currentQuetionTitle').html('(综合题)'+'('+contestDetailPage.data.scoreList[index]+'分)'+contestDetailPage.data.questions[index].description);
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  id="questionAnswer" rows="20"></textarea>\n' +
                '                    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.questionsAnswer[index].answerList[0] != '') {
                $('#questionAnswer').val(contestDetailPage.data.questionsAnswer[index].answerList[0]);
            }
        }
        contestDetailPage.buttonShow()
        contestDetailPage.saveMyAnswers();

    },
    //按钮显示
    buttonShow: function (){
        var currentQuestionButtonStr = '';
        for (var i = 0; i < contestDetailPage.data.questions.length; i++) {
            var buttonStr = '';
            if (contestDetailPage.data.currentQuestionIndex == i) {
                buttonStr = '<button class="mini ui positive button" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
            } else if (contestDetailPage.data.questionsAnswer[i].answerList[0] != '') {
                var flag=1;
                for (var j=0;j<contestDetailPage.data.questionsAnswer[i].answerList.length;j++){
                    if (contestDetailPage.data.questionsAnswer[i].answerList[j]==''){
                        flag=0;
                    }
                }
                if (flag==1){
                    buttonStr = '<button class="mini ui green basic button" onclick="contestDetailPage.targetQuestionAction('+i+')" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
                }else{
                    buttonStr = '<button class="mini ui button" onclick="contestDetailPage.targetQuestionAction('+i+')" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
                }
            } else {
                buttonStr = '<button class="mini ui button" onclick="contestDetailPage.targetQuestionAction('+i+')" style="margin-top: 10px;margin-left: 5px;">'+(i+1)+'</button>';
            }
            currentQuestionButtonStr += buttonStr;
        }
        $('#currentQuestionButton').html(currentQuestionButtonStr);
    },
    //交卷事件触发
    finishContestAction: function () {
        contestDetailPage.saveCurrentAnswer();
        //TODO::交卷
        contestDetailPage.submittingContestAction();
    },
    //保存当前题目答案
    saveCurrentAnswer:function (){
        var index = contestDetailPage.data.currentQuestionIndex;
        //记录答案
        if (contestDetailPage.data.questions[index].type == 'singleChoiceQuestion') {
            contestDetailPage.data.questionsAnswer[index].answerList[0] = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                contestDetailPage.data.questionsAnswer[index].answerList[0] += $(this).val();
                // contestDetailPage.data.questions[preIndex].answer += ',';
            });
        } else if (contestDetailPage.data.questions[index].type == 'multipleChoiceQuestion') {
            contestDetailPage.data.questionsAnswer[index].answerList[0] = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                //console.log($(this).val());
                contestDetailPage.data.questionsAnswer[index].answerList[0] += $(this).val();
                contestDetailPage.data.questionsAnswer[index].answerList[0] += ',';
            });
        } else if (contestDetailPage.data.questions[index].type == 'completion') {
            for (var i=0;i<contestDetailPage.data.questions[index].answers.length;i++){
                var answerStr = '#'+i;
                contestDetailPage.data.questionsAnswer[index].answerList[i] = $(answerStr).val();
            }
        } else if (contestDetailPage.data.questions[index].type == 'judgment') {
            contestDetailPage.data.questionsAnswer[index].answerList[0] = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                //console.log($(this).val());
                contestDetailPage.data.questionsAnswer[index].answerList[0] += $(this).val();
                // contestDetailPage.data.questions[preIndex].answer += ',';
            });
        } else if (contestDetailPage.data.questions[index].type == 'combinationChoice') {
            contestDetailPage.data.questionsAnswer[index].answerList[0] = '';
            $.each($("input[name='questionAnswer']:checked"),function(){
                //console.log($(this).val());
                contestDetailPage.data.questionsAnswer[index].answerList[0] += $(this).val();
                contestDetailPage.data.questionsAnswer[index].answerList[0] += ',';
            });
        } else if (contestDetailPage.data.questions[index].type == 'programProblem') {
            contestDetailPage.data.questionsAnswer[index].answerList[0] = $("#questionAnswer").val();
        } else {
            //console.log($("#questionAnswer").val());
            contestDetailPage.data.questionsAnswer[index].answerList[0] = $("#questionAnswer").val();
        }
    },
    //向后端提交答卷，保证可靠性
    saveMyAnswers: function (){
        contestDetailPage.saveCurrentAnswer()
        let data=[]
        contestDetailPage.data.questionsAnswer.forEach(function(s){
            data.push(JSON.stringify(s))
        })
        //向后端API发送答题卡
        $.ajax({
            url : "/student/test/saveMyAnswer",
            type : "POST",
            traditional: true,
            <!-- 向后端传输的数据 -->
            data : {"answers":data,
                "examId":contestDetailPage.data.contest.id,
                "antiCount":contestDetailPage.data.antiCount},
            success:function() {
                console.log('保存成功')
            },
        });
    },
    //代码评判
    submitCode: function (){
        var code = $("#questionAnswer").val();
        var programProblemButton = '<button class="ui basic loading button">提交代码</button>'
        $("#programProblemButton").html(programProblemButton)
        var id = contestDetailPage.data.questionsAnswer[contestDetailPage.data.currentQuestionIndex].subjectId
        jQuery.post("/student/oj/judge",{'code':code,'id':id},function (rst){
            if (rst == -1){
                var codeResult = '得分：0分！出现语法错误！'
                $("#programProblemCodeSCore").html(codeResult);
                programProblemButton = '<button class="ui positive basic button" onclick="contestDetailPage.submitCode()">提交代码</button>'
                $("#programProblemButton").html(programProblemButton)
            }else{
                var caseNum = contestDetailPage.data.questions[contestDetailPage.data.currentQuestionIndex].inputs.length
                var score = rst/caseNum * contestDetailPage.data.scoreList[contestDetailPage.data.currentQuestionIndex]
                var codeResult = '得分：'+score+'分！通过测试用例 '+rst+'/'+caseNum;
                $("#programProblemCodeSCore").html(codeResult);
                contestDetailPage.data.questionsAnswer[contestDetailPage.data.currentQuestionIndex].score = score
                programProblemButton = '<button class="ui positive basic button" onclick="contestDetailPage.submitCode()">提交代码</button>'
                $("#programProblemButton").html(programProblemButton)
            }
        })
    },
    //正在交卷
    submittingContestAction: function () {
        $('#waitSubmitModal').modal({
            /**
             * 必须点击相关按钮才能关闭
             */
            closable  : false,
            /**
             * 模糊背景
             */
            blurring: true,
        }).modal('show');

        let data=[]
        contestDetailPage.data.questionsAnswer.forEach(function(s){
            data.push(JSON.stringify(s))
        })
        //向后端API发送答题卡
        $.ajax({
            url : "/student/test/submitExam",
            type : "POST",
            traditional: true,
            <!-- 向后端传输的数据 -->
            data : {"answers":data,
                    "examId":contestDetailPage.data.contest.id,
                    "antiCount":contestDetailPage.data.antiCount},
            success:function(result) {
                if (result=='success') {
                } else {
                    //TODO::发送答题卡出错处理
                    console.log(result.message);
                }
            },
            // error:function(result){
            //     //TODO::发送答题卡出错处理
            //     console.log(result);
            // }
        });

        setTimeout(function () {
            $("#waitSubmitModal").modal("hide");
            window.location.href = '/student/exam/list?cno='+contestDetailPage.data.contest.classID
        }, 5000);
    },

};