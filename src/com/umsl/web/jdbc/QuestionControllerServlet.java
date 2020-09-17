package com.umsl.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class QuestionControllerServlet
 */
@WebServlet("/QuestionControllerServlet")
public class QuestionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private QuestionDbUtil questionDbUtil;
	
	@Resource(name="jdbc/question")

	private DataSource dataSource;
		
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		//create our question db util and pass in the conn pool /datasource
		try {
			questionDbUtil = new QuestionDbUtil(dataSource);
		}
		catch(Exception exec) {
			throw new ServletException(exec);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
		try {
			
		// read the command
		 String theCommand = request.getParameter("command");
		 //if command is missing
		 if (theCommand == null)
		 {
			 theCommand ="LIST";
		 }
		 
		 //route the page on appropriate method
		 switch (theCommand) {
		 case "LIST":
			 
				listQuestions(request,response);
				break;
		 case "ADD":
		 
			 	addQuestions(request,response);
			 	break;
		  
		 default:
				listQuestions(request,response);
			 
		 }
	
		}
		catch(Exception exec)
		{
			throw new ServletException(exec);
		}
	}


		
	private void addQuestions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//read the question information from the form data
	 
		String question = request.getParameter("question");
 
				
		//create a new question  to object
		Question theQuestion = new Question(question);
		 
		//add question to the database
		questionDbUtil.addQuestion(theQuestion);
		//send back to the main page
		listQuestions(request,response);
	}

	private void listQuestions(HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		// TODO Auto-generated method stub
		
		//get the questions from db Util
		List<Question> questions = questionDbUtil.getQuestions();
		//add questions to the request
		request.setAttribute("QUESTIONS_LIST",questions);
		//send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_questions.jsp");
		dispatcher.forward(request, response);
	}


}
