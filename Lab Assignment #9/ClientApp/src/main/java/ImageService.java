import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.input.SearchHit;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;

/**
 * Created by Naga on 13-03-2017.
 * Modified by Raj on 03-23-2017 to use Clarifai to pull similar images
 */
@WebServlet(name = "ImageService", urlPatterns = "/ImageService")
public class ImageService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String response="";

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println(data);
        String output = "";
        JSONObject params = new JSONObject(data);
        JSONObject result = params.getJSONObject("result");
        JSONObject parameters = result.getJSONObject("parameters");

        final ClarifaiClient client = new ClarifaiBuilder("cT-RYP0YjP0jlO4ASrLyPoGP768jOVD6mUfmoMDr", "sZ2vVaKHKdyFITb0BLLXTa0f9qOh86UoAO_uub6K")
                .client(new OkHttpClient())
                .buildSync();
        client.getToken();

        if (parameters.get("animals").toString().equals("animals")) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            // Get all the images from Clarifai that match the concept "animals"
            ClarifaiResponse<List<SearchHit>> animalImages = client.searchInputs(SearchClause.matchConcept(Concept.forName("animals")))
                    .build()
                    .getPage(6)
                    .executeSync();

            JSONObject myjsonObj = new JSONObject(animalImages);
            //Grab all the hits from animalImages
            JSONArray myhits = myjsonObj.getJSONArray("hits");

            for(int i = 0 ; i < myhits.length() ; i++) {
                JSONObject p = (JSONObject) myhits.get(i);
                String myurl = p.getString("url");
                //Add all the images to the output
                jsonArray.put(myurl);
            }

            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "animals are displayed");
            js.put("displayText", "animals are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("animals").toString().equals("horse")) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            // Get all the images from Clarifai that match the concept "horse"
            ClarifaiResponse<List<SearchHit>> horseImages = client.searchInputs(SearchClause.matchConcept(Concept.forName("horse")))
                    .build()
                    .getPage(6)
                    .executeSync();

            JSONObject myjsonObj = new JSONObject(horseImages);
            //Grab all the hits from animalImages
            JSONArray myhits = myjsonObj.getJSONArray("hits");

            for(int i = 0 ; i < myhits.length() ; i++) {
                JSONObject p = (JSONObject) myhits.get(i);
                String myurl = p.getString("url");
                //Add all the images to the output
                jsonArray.put(myurl);
            }

            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "horses are displayed");
            js.put("displayText", "horses are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("animals").toString().equals("cat")){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            // Get all the images from Clarifai that match the concept "cat"
            ClarifaiResponse<List<SearchHit>> catImages = client.searchInputs(SearchClause.matchConcept(Concept.forName("cat")))
                    .build()
                    .getPage(6)
                    .executeSync();

            JSONObject myjsonObj = new JSONObject(catImages);
            //Grab all the hits from catImages
            JSONArray myhits = myjsonObj.getJSONArray("hits");

            for(int i = 0 ; i < myhits.length() ; i++) {
                JSONObject p = (JSONObject) myhits.get(i);
                String myurl = p.getString("url");
                //Add all the images to the output
                jsonArray.put(myurl);
            }

            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "cats are displayed");
            js.put("displayText", "cats are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("animals").toString().equals("dog")){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            // Get all the images from Clarifai that match the concept "dog"
            ClarifaiResponse<List<SearchHit>> dogImages = client.searchInputs(SearchClause.matchConcept(Concept.forName("dog")))
                    .build()
                    .getPage(6)
                    .executeSync();

            JSONObject myjsonObj = new JSONObject(dogImages);
            //Grab all the hits from animalImages
            JSONArray myhits = myjsonObj.getJSONArray("hits");

            for(int i = 0 ; i < myhits.length() ; i++) {
                JSONObject p = (JSONObject) myhits.get(i);
                String myurl = p.getString("url");
                //Add all the images to the output
                jsonArray.put(myurl);
            }

            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "dogs are displayed");
            js.put("displayText", "dogs are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("null").toString().equals("clear")){
            Data data_ob = Data.getInstance();
            JSONObject js1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(" ");
            js1.put("data", jsonArray);
            data_ob.setData(js1.toString());
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "screen is cleared");
            js.put("displayText", "screen is cleared");
            js.put("source", "image database");
            response = js.toString();
        }
        resp.setHeader("Content-type", "application/json");
        resp.getWriter().write(response);
    }
}
