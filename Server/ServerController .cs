using System.Security.Cryptography;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

[ApiController]
[Route("api/message")]
public class ServerController : ControllerBase
{
	private readonly IHubContext<ServerHub> _hub;
	private readonly ILogger<ServerController> _logger;

	public ServerController(
		IHubContext<ServerHub> hub,
		ILogger<ServerController> logger
		)
	{
		_hub = hub;
		_logger = logger;
	}

	[HttpPost("{userId}")]
	public async Task<IActionResult> SendToUser(
		string userId,
		 [FromBody] ControllerMessage controllerMessage
		 )
	{
		var message = controllerMessage.toMessage();
		LogSendToUser(message, userId);
		await _hub.Clients.Group(userId).SendAsync(HubConfig.MESSAGE_METHOD_NAME, controllerMessage.Type, controllerMessage.Text);
		return Ok();
	}

	private void LogSendToUser(MessageDto message, string userId)
	{
		_logger.LogInformation(
		"Sending message '{Text}', id '{Id}', type {Type}, to client '{UserId}'",
		message.Text,
		message.Id,
		message.Type,
		userId
	);
	}

	[HttpPost("broadcast")]
	public async Task<IActionResult> Broadcast(
		[FromBody] ControllerMessage controllerMessage
		)
	{
		var message = controllerMessage.toMessage();
		LogBroadcast(message);
		await _hub.Clients.All
			.SendAsync(HubConfig.MESSAGE_METHOD_NAME, message);
		return Ok();
	}

	private void LogBroadcast(MessageDto message)
	{
		_logger.LogInformation(
		"Broadcasting message '{Text}', id '{Id}', type {Type} to all clients",
		message.Text,
		message.Id,
		message.Type
	);
	}
}

public class ControllerMessage
{
	public required int Type { get; set; }
	public required string Text { get; set; }

	public MessageDto toMessage()
	{
		return new MessageDto
		{
			Id = Guid.NewGuid().ToString(),
			Type = this.Type,
			Text = this.Text
		};
	}
}

