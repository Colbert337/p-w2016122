var openUrl ;
var openIframe;
var tree ;
var selectedId ="";
var isfull = "true";
var isDblClick = "false";
function loadtree(id,img,url){
	tree = new dhtmlXTreeObject(id,"100%","100%",-1);	
	tree.setImagePath(img);	
	tree.setXMLAutoLoading(url);	
	tree.setOnClickHandler(onNodeClicked);
	tree.loadXML(url);
}
function dialogtree(id,img,url){

	tree = new dhtmlXTreeObject(id,"100%","100%",-1);
	tree.setImagePath(img);
	tree.setXMLAutoLoading(url);
	tree.loadXML(url);
	if(isDblClick == "true"){
		tree.setOnDblClickHandler("onDblClick");
	}
}
function setTreeIdFomat(data){
	isfull = data;
}
function onNodeClicked(id){

	selectedId = id;	
	if(isfull == "true"){
		id = id.split(":")[1];
	}
	
	if (openUrl) {
		window.open(openUrl+id + "&" + Math.random(),openIframe);
	}
}
	
	
function updateTree(notClicked) {
	if(selectedId == ""){
		//selectedId = "-1";
		selectedId = tree.getSelectedItemId();
	}
	tree.refreshItem(selectedId);
	tree.selectItem(selectedId,false,false);
	if (!notClicked) {
		onNodeClicked(selectedId);
	}	
}

function updateParentTree(notClicked) {
	var parentId;
	if(selectedId == "" || selectedId == "-1"){
		//selectedId = "-1";
		selectedId = tree.getSelectedItemId();
	} else {
		if(tree.getParentId(selectedId) != "-1" && tree.getParentId(selectedId) != "") {
			selectedId = tree.getParentId(selectedId);	
		}
	}
	
	tree.refreshItem(selectedId);
	tree.selectItem(selectedId,false,false);
	if (!notClicked) {
		onNodeClicked(selectedId);
	}	
}
	
function setDblClick(data){
	isDblClick = data;
	if(data == "true"){
		tree.setOnDblClickHandler("onDblClick");
	}
}

function onDblClick(id){
	
}
 
function setOpenUrl(url,iframe) {
	openUrl = url;
	openIframe = iframe;
}

function doUp(data){
	var Result = document.getElementById(data);
	var pos = Result.selectedIndex;
	if(pos > 0)
	{
		var o = new Option(Result.options[pos-1].text,Result.options[pos-1].value);
		Result.options[pos-1] = new Option(Result.options[pos].text,Result.options[pos].value);
		Result.options[pos] = new Option(o.text,o.value);
		Result.selectedIndex = --pos;
	}
}
function doDown(data){
	var Result = document.getElementById(data);
	var pos = Result.selectedIndex;
	if(pos > -1){
		if(pos < Result.length - 1)
		{
			var o = new Option(Result.options[pos+1].text,Result.options[pos+1].value);
			Result.options[pos+1] = new Option(Result.options[pos].text,Result.options[pos].value);
			Result.options[pos] = new Option(o.text,o.value);
			Result.selectedIndex = ++pos;
		}
	}
}
function doAdd(data){
	
	var Result = document.getElementById(data);
	var id = tree.getSelectedItemId();
	if(id != ""){
		var text = tree.getItemText(id);
		if(Result.options.length == 0){
			Result.options[Result.options.length] = new Option(text,id);
		} else { 
			for(var i=0;i<Result.options.length ; i++)
			{	
				if (Result.options[i].value == id)
				{
					return ;
				}
			}
			Result.options[Result.options.length] = new Option(text,id);
		}
	}
}

function doLastAdd(data){
	var Result = document.getElementById(data);
	var id = tree.getSelectedItemId();
	if(id != "" && id != "0" && tree.hasChildren(id)==0){
		var text = tree.getItemText(id);
		if(Result.options.length == 0){
			Result.options[Result.options.length] = new Option(text,id);
		} else { 
			for(var i=0;i<Result.options.length ; i++) {	
				if (Result.options[i].value == id) {
					return ;
				}
			}
			Result.options[Result.options.length] = new Option(text,id);
		}
	}
}

function doCheckedAdd(data){
	var Result = document.getElementById(data);
	var list=tree.getAllChecked();
    var ids=list.split(",");
    for (i=0;i<ids.length;i++){
	     if (ids[i].indexOf("-1")>-1){
	     	//alert ("根节点")
	     }else{
	     	text=tree.getItemText(ids[i]);
	     	if(Result.options.length == 0){
				Result.options[Result.options.length] = new Option(text,ids[i]);
			} else { 
				for(var j=0;j<Result.options.length ; j++) {	
					if (Result.options[j].value == ids[i]) {
						return ;
					}
				}
				Result.options[Result.options.length] = new Option(text,ids[i]);
			}
	     }	
     }
}

function doSingleAdd(data){
	var Result = document.getElementById(data);
	var id = tree.getSelectedItemId();
	if(id != ""){
		var text = tree.getItemText(id);
		if(Result.options.length == 0){
			Result.options[Result.options.length] = new Option(text,id);
		} else{
			Result.options.length = 0;
			Result.options[Result.options.length] = new Option(text,id);
		}
	}
}

function doLsAdd(data){
	var Result = document.getElementById(data);
	var id = tree.getSelectedItemId();
	if(id != "" && id != "0" && tree.hasChildren(id)==0){
		var text = tree.getItemText(id);
		if(Result.options.length == 0){
			Result.options[Result.options.length] = new Option(text,id);
		} else { 
			for(var i=0;i<Result.options.length ; i++)
			{	
				if (Result.options[i].value == id)
				{
					return ;
				}
			}
			Result.options.length = 0;
			Result.options[Result.options.length] = new Option(text,id);
		}
	}
}

function doAddUser(data){
	
	var Result = document.getElementById(data);
	var id = tree.getSelectedItemId();
	if(id != ""){
		var arr = id.split(":");
		if(arr[0] == "U"){
			var text = tree.getItemText(id);
			if(Result.options.length == 0){
				Result.options[Result.options.length] = new Option(text,id);
			} else { 
				for(var i=0;i<Result.options.length ; i++)
				{	
					if (Result.options[i].value == id)
					{
						return ;
					}
				}
				Result.options[Result.options.length] = new Option(text,id);
			}
		}
	}
}

function doAddRole(data){
	
	var Result = document.getElementById(data);
	var initid = tree.getSelectedItemId();
	var id = initid;
	var rolename = "";
	var orgid = "";
	var text = "";
	if(id != ""){
		var arr = id.split(":");
		if(arr[0] == "R"){
		
			rolename = tree.getItemText(id);
			orgid = tree.getParentId(initid);
		
			if(document.getElementById("checkbox").status){
				text = "["+tree.getItemText(id)+"]";
			}else{
				text = "("+tree.getItemText(id)+")";
			}
			var temp ="";
			for(;;){
				if(id.split(":")[0] == "C"){
					break;
				}else{
					id = tree.getParentId(id);
					if(temp == ""){
						temp = tree.getItemText(id);
					}else{
						temp = tree.getItemText(id)+"/"+temp
					}
				}
			}
			if(document.getElementById("checkbox").status){
				text = "["+temp+"]"+text;
			}else{
				text = "("+temp+")"+text;
			}
			
			if(Result.options.length == 0){
				Result.options[Result.options.length] = new Option(text,orgid);
			} else { 
				for(var i=0;i<Result.options.length ; i++)
				{	
					if (Result.options[i].text == text)
					{
						return ;
					}
				}
				Result.options[Result.options.length] = new Option(text,orgid);
			}
		}
	}
}

function doDelete(data){
	var Result = document.getElementById(data);
	if (Result.length != 0)
	{ 
		if (Result.selectedIndex != -1)
		{
			for(var i=0;i<Result.options.length ; ++i)
			{
				if (Result.options[i].selected)
				{
					Result.options[i] = null;
					--i;
				}
			}
		}
	}
}

function doDeleteAll(data){
	var Result = document.getElementById(data);
	Result.options.length=0;
}
function defaultInit(data,purpose){
	if(!document.getElementById(purpose)){
		alert("defaultInit方法的参数错误，请确认第二个参数是否是页面元素的名称!");	
		return ;	
	}else{
		var Result = document.getElementById(purpose);
		if(data != ""){
			var arrs = data.split(";");
	     	for(var n=0;n<arrs.length;n++){
	     		Result.options[Result.options.length] = new Option(arrs[n++],arrs[n]);
	     	}
		}
	}
}
function doOK(data){
	var retVal = "";
	var Result = document.getElementById(data);
	if (Result.length != 0)
	{
		for(var i = 0;i < Result.length; i++)
		{
			if(i > 0)
			{
				retVal = retVal + ";" ;
			}
			retVal = retVal + Result.options[i].text + ";" + Result.options[i].value  ;
		}
	}
    window.returnValue = retVal;
	window.close();
}
function doCancel() {
	window.returnValue = "-1";
	window.close();
}