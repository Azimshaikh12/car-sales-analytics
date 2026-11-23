package com.azim.carsales.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AiQueryServiceImpl implements AiQueryService{

    private final ChatClient chatClient;
    private final JdbcTemplate jdbcTemplate;
    public AiQueryServiceImpl(ChatClient.Builder builder, JdbcTemplate jdbcTemplate) {
        this.chatClient = builder.build();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String process(String question) {
        String sql = generateSQL(question);
        if (sql.equalsIgnoreCase("INVALID")){
            System.out.println("Only data related Query");
        }
        if(!isSafe(sql)){
            System.out.println("Unsafe query");
        }
        try{
            List<Map<String,Object>> result = jdbcTemplate.queryForList(sql);
            if (result.isEmpty()){
                return "Data not found";
            }
            System.out.println(result);
            return toNaturalLanguage(question,result);
        }
        catch (Exception e){
            return "Query Failed";
        }
    }
    private String toNaturalLanguage(String question,List<Map<String,Object>> result){
        String prompt = """
            Convert database result into a human readable answer.

            User Question:
            """ + question + """

            DB Result:
            """ + result.toString() + """

            Rules:
            Answer clearly (don't write too much)
            Do not show JSON
            Do not explain SQL
            
            """;
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content()
                .trim();
    }
    private boolean isSafe(String query){
        String lower = query.toLowerCase();
        return lower.contains("select")
                && !lower.contains("drop")
                && !lower.contains("insert")
                && !lower.contains("update")
                && !lower.contains("delete");
    }

    private String generateSQL(String question){

        String prompt = """
                You are a SQL generator.
                
                Table: car_sales
                Columns: id, brand, car_number, city, color, contact_number, customer_name, date_of_purchase, email, engine, fuel_type, mileage, model, payment_mode, price, state, time_of_purchase, warranty_period, year
                
                Rules:
                Only SELECT queries
                Use only given columns
                If not related, return: INVALID
                Return only SQL (Do not write or add any extra statement with it only pure sql,for example your sql is this,here is your sql...etc)
                
                Question
                """+question;
        return chatClient.prompt().user(prompt).call().content().trim();
    }
}
