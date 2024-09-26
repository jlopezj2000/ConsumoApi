import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joselop.consumoapi.R
import com.joselop.consumoapi.model.User

class UserAdapter(
    private val users: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], onItemClick)
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userImage: ImageView = itemView.findViewById(R.id.imageViewUser)
        private val userName: TextView = itemView.findViewById(R.id.textViewName)
        private val userEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        private val userCountry: TextView = itemView.findViewById(R.id.textViewCountry)

        fun bind(user: User, onItemClick: (User) -> Unit) {
            userName.text = "${user.name.first} ${user.name.last}"
            userEmail.text = user.email
            userCountry.text = user.location.country

            // Cargar imagen del usuario usando Glide
            Glide.with(itemView.context).load(user.picture.large).into(userImage)

            itemView.setOnClickListener { onItemClick(user) }
        }
    }
}
