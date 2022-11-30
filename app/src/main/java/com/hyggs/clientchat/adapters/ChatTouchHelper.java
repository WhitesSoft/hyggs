package com.hyggs.clientchat.adapters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hyggs.clientchat.R;

public class ChatTouchHelper extends ItemTouchHelper.Callback {

    CallBackItemTouch callBackItemTouch;

    public ChatTouchHelper(CallBackItemTouch callBackItemTouch) {
        this.callBackItemTouch = callBackItemTouch;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        // Capturamos el movimiento del RecyclerView (CHAT)
        final int swipeFlags = ItemTouchHelper.START;

        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // Cuando movemos nuestro elemento, lo que hacemos es obtener la posición y enviar las interfaces
        callBackItemTouch.itemTouchOnMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Lo mismo pero deslizando
        callBackItemTouch.onSwiped(viewHolder, viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        // Mostraremos el botón eliminar cuando deslizamos
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        } else {
            final View view = ((AdapterChatsRecyclerView.ChatsViewHolder) viewHolder).viewDeleteRelativeLayout;
            getDefaultUIUtil().onDrawOver(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        // Deslizamos con animacion suave
        if (actionState != ItemTouchHelper.ACTION_STATE_DRAG) {
            final View view = ((AdapterChatsRecyclerView.ChatsViewHolder) viewHolder).viewChatRelativeLayout;
            getDefaultUIUtil().onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive);
        }

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // Establece el color del elemento cuando lo arrastremos y dejemos cualquier posición para que queramos su color original
        final View view = ((AdapterChatsRecyclerView.ChatsViewHolder) viewHolder).viewChatRelativeLayout;
        view.setBackgroundColor(ContextCompat.getColor(((AdapterChatsRecyclerView.ChatsViewHolder) viewHolder)
                .viewChatRelativeLayout.getContext(), R.color.black));
        getDefaultUIUtil().clearView(view);
        // Esto borrará la vista cuando pasemos y arrastremos
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        // En esto cambiamos de color y luego el elemento seleccionado
        if (viewHolder != null) {
            final View view = ((AdapterChatsRecyclerView.ChatsViewHolder) viewHolder).viewChatRelativeLayout;
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                view.setBackgroundColor(Color.LTGRAY);
            }
            getDefaultUIUtil().onSelected(view);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
