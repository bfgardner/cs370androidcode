package io.iaso.iaso.network.async;

/*public class NotificationResponseTask extends AsyncTask<String,String,NotificationResponse> {
    private OnNotificationCallbackListener listener;

    @Override
    protected NotificationResponse doInBackground(String... params) {
        String myParams = "";

        if(params.length > 0) {
            for(int i = 0; i < params.length; i++) {
                myParams = myParams + params[i] + "+";
            }
        }
        //none of this will work; gotta wait for the actual ApplicationInstance to be up with the key + id for the api and all that
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(ApplicationInstance.getInstance().getBaseApiUrl()).newBuilder();
        urlBuilder.addQueryParameter("_app_key", ApplicationInstance.getInstance().getApiKey());
        urlBuilder.addQueryParameter("_app_id", ApplicationInstance.getInstance().getAppId());
        urlBuilder.addQueryParameter("q", myParams);

        String url = urlBuilder.build().toString();

        Response response = null;

        try {
            response = client.newCall(request).execute();

            if (response != null) {
                Gson gson = new Gson();
                NotificationResponse notificationResponse = gson.fromJson(response.body().string(), NotificationResponse.class);

                return notificationResponse;
            }
        } catch (IOException e) {
            // do something with exception
        }

        return null;
    }

    @Override
    protected void onPostExecute(NotificationResponse response) {
        super.onPostExecute(response);
        listener.onCallBack(response);
    }

    public void setOnNotificationCallbackListener(OnNotificationCallbackListener listener) {
        this.listener = listener;
    }

    public interface OnNotificationCallbackListener {
        void onCallBack(NotificationResponse response);
    }
}*/