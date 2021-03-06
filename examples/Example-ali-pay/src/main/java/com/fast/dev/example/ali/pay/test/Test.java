package com.fast.dev.example.ali.pay.test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;

public class Test {

	public static void main(String[] args) throws AlipayApiException {
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2016081600258859", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCulHj+xbt+dqLt8w0JV08q2LvOVfYYQ2XR74sCHN3MRwGXDqWvfGggDNV0CjNLPoZgimpl9/sW2HZmVITC4Qn4EBhTAa/XyGkZFvv4dwOaBmu9YQvrPJwbyNpYJ1z6CbG3/5l95bLaqQHKYxoU3xWCvjYYdh80EwPcZoy6CnA029gXutkoYfAUJHTpmsR/qLDGo12Amk8PUdaeUr69pCoS8C0htkqc0D1vVy7YyyM1wKggxcbUMlqQTB7rOlrFP/YK9i8xXh4MjUdspu0ibyjT1buq+5ZuPmIx3F/IGNsS7YKWU5M6HT3fSpkh5hwzCBD2Hk+qF/mRoCNhmSt1/KnlAgMBAAECggEBAIBG8jzp16HN1FwZOhwB/lbSV/T97szXQQqoA7Eyurqt9fojthSiyfO62mDgWd5Q+/YV3qY8N1ALD7yTH9X9+xA961GarLVWOftIiy5/bLMRQrstgs9/golb5SvfCI9k7iPv+L2sOOY4RuuKNBDf9nNSBnek27qsuiqJY6ATqWz8X3B4QKhKbtZsfDPhJ5b79DisqmV2l+XwNlWN/SWQQE7bDSmt3tn9Qcwz40qyDbFRuojD5xdVcnwkWXB9xOg7uKCR+cS2bGvtulhhgVeeFfl9AcPUQgvkX4RcU5Ie6aIemvX3b/hpcPkc7M1S8cf3j16abPVXebsXnV6YrdV8LoUCgYEA8aFP9JnG1e8UZnhgIu8kmx+oHbF9ZRUp21Qyb9cNioJ2zlnWuLtf0ly2WPbJxq51i07bybDJR/bdUZ6VqUM9kQXZRW+XhhwpfqRsikVKIGCUZ6d4S3yBD5x1q3Isu3ldNsh8ZDokx+kMFhTRzTkAdqyc5sUtBKQMYSex9sGqdf8CgYEAuPZbqUt7xJkIflXE5ZQtCACYvuF0KpIyrSCQDOBW+pT1g2UGO0PbU9azZvfy2KNCb/eu3Plo7L1jDjHWjpHq1Cs4qWVQ4zbNNothSTqIDzWWdxnuVvlW5NJ2qJsVRvXo1iCvgmaUvPN24TL2s8QvjUhtawAqiafI4tGH1OEtyBsCgYBWD2xY5ERrggG0FX+4MZDKJ1ZLbSIKVKVZ1v031FKgWKNUD69okGQqmpbfzNZFC3IWMfoaNfsaxJRDM8Z+SNKuynR+RKalP3aQAqQeA1vJeFyjJoSz7h5RnxpR/BJum2VV08ZqNENRaHHDk5q+C2Vc31Hq56OHGZ7HI8pA5u19pQKBgD7zUfUHhMM/ll1xEYdVbBIaKeJlVyUaBvdXas+HFvNH+VtT6dAH40DghMSZxOQohHcZJEF+/U/9kRYGrlBtpy+42hsFKYd7YlKbE2xZ6G7MvT4h7DVbvVA5tTrjNVWmYi1miHVoNl0su5wfvUknj0uVh+/v6YpaOYvA5mr0jKFbAoGBAO9DAYFpwsr5BeEYK4PEYLKhx1c96yzvfF681tG7HOG8Lu0TviRcsgeam5l66les/NcnQDgfC690ih2tirGn+lMQchRBRC+DxBDswZ13g+128zmSeiko+5tCR0QugoRRIvgewus024yDucWbFQbrdzLUYTjEYmrhcO6JlFEUGe3C", "json", "UTF-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsSOq1lIMztET+Dlf5A/LyRoD1gEG/h3GKZgdzKBc3Wa06faJFBkf5ACkg4Esq+mi3l6Fm3sSQbZxeS/wUXmP0LuE9HXy38sSOWImIzA66ItXwDr7KdO4NLBYA9lJoqBMEaXgzdxfHs/bK5WdW34h+bxRfQERJx0DWUWTDLWDQA58Io9BouKpEdIC+QbqfVBjOiUEHfB/MGqCyXI4VytEVVU8A+YpO3V1Ag7J/PcayS08A/vfrjPx3N7Vuplt5xMZxR5gEnEnPN0dsx8iWYUi1hqa5nSeET6xGYevA5VBRtOhUr5bGe5tn02UAxLKQRstSEABJ56asbd8u7hshEl6nwIDAQAB", "RSA2");

		AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
		
		request.setBizContent("  {" +
				"    \"primary_industry_name\":\"IT科技/IT软件与服务\"," +
				"    \"primary_industry_code\":\"10001/20102\"," +
				"    \"secondary_industry_code\":\"10001/20102\"," +
				"    \"secondary_industry_name\":\"IT科技/IT软件与服务\"" +
				" }");
		
		AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request); 
		
		if(response.isSuccess()){
			
			System.out.println(response);
		    //.....Syso
		}
		
	}

}
