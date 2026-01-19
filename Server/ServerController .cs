using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

[ApiController]
[Route("api/message")]
public class ServerController : ControllerBase
{
		private readonly IHubContext<ServerHub> _hub;

	public ServerController(IHubContext<ServerHub> hub)
	{
		_hub = hub;
	}

	[HttpPost("{userId}")]
	public async Task<IActionResult> Push(
		string userId,
		 [FromBody] Message message
		 )
	{
		Console.WriteLine($"Sending message: '{message.Text}', with type: {message.Type}, to clinet: '{userId}");
		await _hub.Clients.Group(userId).SendAsync(HubConfig.MESSAGE_METHOD_NAME, message.Type, message.Text);
		return Ok();
	}

	[HttpPost("broadcast")]
	public async Task<IActionResult> Broadcast(
		[FromBody] Message message
		)
	{
		Console.WriteLine($"Sending message: '{message.Text}', with type: {message.Type}, to all clinets");
		await _hub.Clients.All
			.SendAsync(HubConfig.MESSAGE_METHOD_NAME, message.Type, message.Text);

		return Ok();
	}
}

