package controllers;

import api.ReceiptSuggestionResponse;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import java.math.BigDecimal;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.NotEmpty;

import static java.lang.System.out;

@Path("/images")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptImageController {
    private final AnnotateImageRequest.Builder requestBuilder;

    public ReceiptImageController() {
        // DOCUMENT_TEXT_DETECTION is not the best or only OCR method available
        Feature ocrFeature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        this.requestBuilder = AnnotateImageRequest.newBuilder().addFeatures(ocrFeature);

    }

    /**
     * This borrows heavily from the Google Vision API Docs.  See:
     * https://cloud.google.com/vision/docs/detecting-fulltext
     *
     * YOU SHOULD MODIFY THIS METHOD TO RETURN A ReceiptSuggestionResponse:
     *
     * public class ReceiptSuggestionResponse {
     *     String merchantName;
     *     String amount;
     * }
     */
    @POST
    public ReceiptSuggestionResponse parseReceipt(@NotEmpty String base64EncodedImage) throws Exception {
        Image img = Image.newBuilder().setContent(ByteString.copyFrom(Base64.getDecoder().decode(base64EncodedImage))).build();
        AnnotateImageRequest request = this.requestBuilder.setImage(img).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse responses = client.batchAnnotateImages(Collections.singletonList(request));
            AnnotateImageResponse res = responses.getResponses(0);

            String merchantName = null;
            BigDecimal amount = null;

            // Your Algo Here!!
            // Sort text annotations by bounding polygon.  Top-most non-decimal text is the merchant
            // bottom-most decimal text is the total amount
            Map<Vertex, String> textMap = new HashMap<>();
            boolean firstAnnotation = true;
            for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                if (firstAnnotation) {
                    firstAnnotation = false;
                    continue;
                }
                Vertex topLeft = annotation.getBoundingPoly().getVertices(0);
                String text = annotation.getDescription();
                textMap.put(topLeft, text);

                //out.println("Position : %s\n"+ annotation.getBoundingPoly());
                //out.println("Text: %s\n"+ annotation.getDescription());
            }
            List<Vertex> vertices = new ArrayList<>();
            vertices.addAll(textMap.keySet());
            Collections.sort(vertices, (a, b) -> (a.getY()==b.getY() ? a.getX()-b.getX() : a.getY()-b.getY()));
            for (int i = 0; i < vertices.size(); i++) {
                String topText = textMap.get(vertices.get(i));
                if (!isNumeric(topText)) {
                    merchantName = topText;
                    break;
                }
            }
            for (int j = vertices.size() - 1; j >= 0; j--) {
                String bottomText = textMap.get(vertices.get(j));
                if (isNumeric(bottomText)) {
                    if (bottomText.charAt(0) == '$') {
                        bottomText = bottomText.substring(1);
                    }
                    amount = new BigDecimal(bottomText);
                    break;
                }
            }

            //TextAnnotation fullTextAnnotation = res.getFullTextAnnotation();
            return new ReceiptSuggestionResponse(merchantName, amount);
        }
    }

    private boolean isNumeric(String str) {
        if (str.length() == 0) {
            return false;
        }
        String digits = "0123456789";
        if (str.charAt(0) == '$' && str.length() > 1) {
            str = str.substring(1);
        }
        boolean hasDot = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!hasDot && c == '.' && str.length() > 1) {
                hasDot = true;
            } else if (digits.indexOf(c) < 0) {
                return false;
            }
        }
        return true;
    }
}