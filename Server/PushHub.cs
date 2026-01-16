using Microsoft.AspNetCore.SignalR;

public class PushHub : Hub
{
	public override async Task OnConnectedAsync()
	{
		Console.WriteLine("OnConnectedAsync()");
		var userId = Context.GetHttpContext()?.Request.Query["userId"];
		if (!string.IsNullOrEmpty(userId))
		{
			Console.WriteLine($"Adding user: {userId}");
			await Groups.AddToGroupAsync(Context.ConnectionId, userId);
		}

		await base.OnConnectedAsync();
	}

	public override async Task OnDisconnectedAsync(Exception? exception)
	{
		Console.WriteLine("OnConnectedAsync()");
		var userId = Context.GetHttpContext()?.Request.Query["userId"];
		if (!string.IsNullOrEmpty(userId))
		{
			Console.WriteLine($"Removing user: {userId}");
			await Groups.RemoveFromGroupAsync(Context.ConnectionId, userId);
		}

        await base.OnDisconnectedAsync(exception);
	}

	public async Task SendPush(string userId, string message)
	{
		await Clients.Group(userId).SendAsync("ReceivePush", message);
	}
}
