package edu.funix.utils;

import static com.slack.api.webhook.WebhookPayloads.payload;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;

public class SlackApiUtil {
    public static WebhookResponse pushLog(HttpServletRequest request, String exception) throws IOException {
	ResourceBundle resource = ResourceBundle.getBundle("slackApi");
	Slack slack = Slack.getInstance();
	String header = request.getHeader("referer");
	String servlet = request.getSession().getId();
	String webhookUrl = resource.getString("SLACK_WEBHOOK_URL");
//	Payload payload = Payload.builder().text(exception).build();
	WebhookResponse response = slack.send(webhookUrl,
		payload(p -> p.blocks(asBlocks(section(section -> section.text(markdownText("From: " + header))),
			section(section -> section.text(markdownText("By: " + servlet))),
			section(section -> section.text(markdownText("Message: " + exception)))))));

	return response;
    }
}
