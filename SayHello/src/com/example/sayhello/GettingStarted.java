package com.example.sayhello;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.QueryRequest;
import com.google.api.services.bigquery.model.QueryResponse;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;

public class GettingStarted {
	
	Logger log = Logger.getLogger(GettingStarted.class.getName());
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		
		Scanner sc;
		
		if(args.length == 0){
			System.out.print("Enter Project ID - ");
			sc = new Scanner(System.in);
		} else {
			sc = new Scanner(args[0]);
		}
		
		String projectId = sc.nextLine();
		
		// Create a new Bigquery client authorized via Application Default Credentials.
	    Bigquery bigquery = createAuthorizedClient();
	    
	    List<TableRow> rows = executeQuery("SELECT TOP(corpus, 10) as title, COUNT(*) as unique_words FROM [publicdata:samples.shakespeare]", projectId, bigquery);
		
	    
	    printResults(rows);
			
	}

	private static Bigquery createAuthorizedClient() throws IOException {
		
		
		GoogleCredential googleCredential = GoogleCredential.getApplicationDefault();

		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		if(googleCredential.createScopedRequired()){
			googleCredential = googleCredential.createScoped(BigqueryScopes.all());
		}
		
		return new Bigquery.Builder(httpTransport, jsonFactory, googleCredential).setApplicationName("Getting Started").build();
	}
	
	private static List<TableRow> executeQuery(String query, String projectId,
			Bigquery bigquery) throws IOException {
		
		QueryResponse queryResponse = bigquery.jobs().query(projectId, new QueryRequest().setQuery(query)).execute();
		
		GetQueryResultsResponse queryResultResponse = bigquery.jobs().getQueryResults(queryResponse.getJobReference().getProjectId(), queryResponse.getJobReference().getJobId()).execute();
		
		return queryResultResponse.getRows();
	}
	
	private static void printResults(List<TableRow> rows) {
		Logger log = Logger.getLogger(SayHelloServlet.class.getName());
		log.info("\nQuery Results:\n------------\n");
		for(TableRow row : rows){
			for(TableCell cell : row.getF()){
				System.out.printf("%-50s", cell.getV());
				log.info(cell.getV().toString());
			}
			System.out.println();
		}
		
	}

}