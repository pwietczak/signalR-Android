using Microsoft.AspNetCore.SignalR;

public class ServerHub : Hub
{
	private readonly ILogger<ServerHub> _logger;

	public ServerHub(ILogger<ServerHub> logger)
	{
		_logger = logger;
	}

	public override async Task OnConnectedAsync()
	{
		_logger.LogInformation("Client connected: {ConnectionId}", Context.ConnectionId);
		var userId = Context.GetHttpContext()?.Request.Query["userId"];
		if (!string.IsNullOrEmpty(userId))
		{
			_logger.LogInformation("Adding user to group: {UserId}", userId);
			await Groups.AddToGroupAsync(Context.ConnectionId, userId);
		}

		await base.OnConnectedAsync();
	}

	public override async Task OnDisconnectedAsync(Exception? exception)
	{
		_logger.LogInformation("Client disconnected: {ConnectionId}", Context.ConnectionId);
		var userId = Context.GetHttpContext()?.Request.Query["userId"];
		if (!string.IsNullOrEmpty(userId))
		{
			_logger.LogInformation("Removing user from group: {UserId}", userId);
			await Groups.RemoveFromGroupAsync(Context.ConnectionId, userId);
		}

        await base.OnDisconnectedAsync(exception);
	}

	public async Task GetAck(ResponseDto response)
	{
		_logger.LogInformation("GetAck called with message: {Message}", response.MessageId);
	}

	//		await Clients.Group(userId).SendAsync(HubConfig.MESSAGE_METHOD_NAME, message);

}
