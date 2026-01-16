using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

[ApiController]
[Route("api/push")]
public class PushController : ControllerBase
{
	private readonly IHubContext<PushHub> _hub;

	public PushController(IHubContext<PushHub> hub)
	{
		_hub = hub;
	}

	[HttpPost("{userId}")]
	public async Task<IActionResult> Push(
		string userId,
		 [FromBody] BroadcastRequest req
		 )
	{
		Console.WriteLine($"Sending message: '{req}', to clinet: '{userId}");
		await _hub.Clients.Group(userId).SendAsync("ReceivePush", req.Message);
		return Ok();
	}

	[HttpPost("broadcast")]
	public async Task<IActionResult> Broadcast(
		[FromBody] BroadcastRequest req
		)
	{
		Console.WriteLine($"Sending message: '{req.Message}' to all clinets");
		await _hub.Clients.All
			.SendAsync("ReceivePush", req.Message);

		return Ok();
	}
}


public record BroadcastRequest(string Message);
