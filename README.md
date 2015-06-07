# excelDbTool
	a tool used xml to generate excel and transfer excel data to xml data, 
it can also generate read xml data code, now it's support generate c++ / java code.
	use it you should define a meta xml data, all the excel struct and code is based on
the meta data.
	please check the project meta folder to learn how to define the meta data.
	after you define the meta data,you can just type these command to generate excel file:
	
	java -jar excel2xml.jar -meta meta -excel excel -lang cpp -code code -xml xml -type 1
	
	use these command to generate xml data from excel data:
	
	java -jar excel2xml.jar -meta meta -lang cpp -excel excel -code code -xml ../../Resources/data -type 3
	
	use these command to generate java / c++ code:
	
	java -jar excel2xml.jar -meta meta -lang cpp -excel excel -code ../../Classes/gencode -xml data -type 2
	
	note: when you used the command to generate java/c++ code , check argument -xml -xmldir,you generate code will used this dir to read xml file.
